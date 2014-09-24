package com.example.FreeFlow;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class PackList extends ListActivity {

    private Global mGlobals =  Global.getInstance();
    String packfile;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        PackAdapter adapter = new PackAdapter(this, R.layout.list_pack, mGlobals.mPacks);

        setListAdapter(adapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        Intent intent = new Intent(getApplicationContext(), ChallengeList.class);
        Bundle b = new Bundle();

        Pack clicked = (Pack) l.getItemAtPosition(position);
        b.putString("name", clicked.getName()); //Your name
        intent.putExtras(b); //Put your id to your next Intent
        packfile = clicked.getmFile();

        try{
            List<Challenge> challenge = new ArrayList<Challenge>();
            readChallenge(getAssets().open(clicked.getmFile()), challenge);
            mGlobals.mChallenge = challenge;
        }
        catch (Exception e){
            e.printStackTrace();
        }


        startActivity(intent);
    }

    private void readChallenge( InputStream is, List<Challenge> challenge){


        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(is);
            NodeList nList = doc.getElementsByTagName("challenge");

            for( int c = 0; c < nList.getLength(); ++c){
                List<Puzzle> cList = new ArrayList<Puzzle>();
                Node nNode = nList.item(c);

                if( nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oNode = (Element) nNode;
                    String name = oNode.getAttribute("name");
                    String stringId = oNode.getAttribute("id");
                    int cId = Integer.parseInt(stringId);

                    NodeList nListInner = doc.getElementsByTagName("puzzle");

                    for(int in = 0; in < nListInner.getLength(); ++in) {
                        Node innerNode = nListInner.item(in);

                        if(innerNode.getParentNode().equals(nNode)) {
                            if (innerNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eNode = (Element) innerNode;
                                String id = eNode.getAttribute("id");
                                String size = eNode.getElementsByTagName("size").item(0).getFirstChild().getNodeValue();
                                String flows = eNode.getElementsByTagName("flows").item(0).getFirstChild().getNodeValue();
                                cList.add(new Puzzle(id, size, flows, packfile, stringId));
                            }
                        }

                    }


                    challenge.add(new Challenge(name, cId, packfile, cList));

                }

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            default:
                  onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
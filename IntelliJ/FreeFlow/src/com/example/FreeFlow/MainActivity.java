package com.example.FreeFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends Activity {

    private Global mGlobals =  Global.getInstance();


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try{
            List<Pack> packs = new ArrayList<Pack>();
            readPack(getAssets().open("packs/packs.xml"), packs);
            mGlobals.mPacks = packs;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            List<Challenge> challenge = new ArrayList<Challenge>();
            readChallenge(getAssets().open("packs/regular.xml"), challenge);
            mGlobals.mChallenge = challenge;
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void buttonClick (View view) {
        Button button = (Button) view;
        int id = button.getId();
        if (id == R.id.buttonPlay) {
            startActivity(new Intent(getApplicationContext(), ChallengeList.class));
        }
    }

    private void readPack( InputStream is, List<Pack> packs){

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(is);
            NodeList nList = doc.getElementsByTagName("pack");

            for( int c = 0; c < nList.getLength(); ++c){

                Node nNode = nList.item(c);
                if( nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eNode = (Element) nNode;
                    String name = eNode.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
                    String description = eNode.getElementsByTagName("description").item(0).getFirstChild().getNodeValue();
                    String file = eNode.getElementsByTagName("file").item(0).getFirstChild().getNodeValue();
                    packs.add(new Pack(name, description, file));


                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
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

                    NodeList nListInner = doc.getElementsByTagName("puzzle");

                    for(int in = 0; in < nListInner.getLength(); ++in) {
                        Node innerNode = nListInner.item(in);

                        if(innerNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eNode = (Element) innerNode;
                            String id = eNode.getAttribute("id");
                            String size = eNode.getElementsByTagName("size").item(0).getFirstChild().getNodeValue();
                            String flows = eNode.getElementsByTagName("flows").item(0).getFirstChild().getNodeValue();
                            cList.add(new Puzzle(id, size, flows));
                        }

                    }


                    challenge.add(new Challenge(name, cList));

                }

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

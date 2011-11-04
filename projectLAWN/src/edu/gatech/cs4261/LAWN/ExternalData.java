package edu.gatech.cs4261.LAWN;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

/**
 * Used for interfacing with the External API
 * @author Robert C. Orwig
 *
 */
public class ExternalData {
//	private static final String DOMAIN = "http://android.imtrackonline.com";
//	private static final int PORT = 80;
//	private static final String API_KEY = "1c0911f25b3d7ad39493d0ff5d5703925d94b6ba";
//	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//	private static final String TAG = "IMMobile ExternalData";
//	
//	/**
//	 * Gets the teams that a participant is a member of
//	 * @param partId the participant's id for which you want the teams
//	 * @return the teams that the participant is a member of
//	 */
//	public static IMTeam[] getTeams(int partId){
//		IMTeam[] teams = null;
//		
//		try {
//			
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			URL url = new URL(DOMAIN + "/api/im/teams/teams_for_participant/" + partId);
//			Document dom = db.parse(url.openStream());
//			NodeList nl = dom.getElementsByTagName("im_team");
//			teams = new IMTeam[nl.getLength()];
//			for(int i = 0; i < teams.length; i++){
//				teams[i] = createTeam((Element)nl.item(i));
//			}
//			
//		} catch (UnknownHostException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (IOException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (ParserConfigurationException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (SAXException e) {
//			Log.e(TAG, e.getMessage());
//		}
//		
//		return teams;
//	}
//	/**
//	 * Gets the roster of a team.  The captain is held at position 0 of the array
//	 * @param teamID the id of the team whose roster you want
//	 * @param partID the id of one of the team members, required so that not just anyone can see info
//	 * @return an array containing each player on the team.  The captain is held at position 0
//	 */
//	public static IMPlayer[] getRoster(int teamID, int partID){
//		IMPlayer[] roster = null;
//		try {
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			URL url = new URL(DOMAIN + "/api/im/teams/team_roster/" + teamID + "?participant_id=" + partID);
//			Document dom = db.parse(url.openStream());
//			Element parent = dom.getDocumentElement();
//			NodeList nl = parent.getElementsByTagName("im_roster");
//			roster = new IMPlayer[nl.getLength() + 1];
//			for(int i = 1; i < roster.length; i++){
//				roster[i] = createPlayer((Element)nl.item(i - 1));
//			}
//			nl = parent.getElementsByTagName("captain");
//			if(nl.getLength() > 0)
//				roster[0] = createPlayer((Element)nl.item(0));
//		} catch (ParserConfigurationException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (UnknownHostException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (IOException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (SAXException e) {
//			Log.e(TAG, e.getMessage());
//		}
//		
//		return roster;
//	}
//	/**
//	 * Gets  a teams schedule
//	 * @param teamId the id of the team you are interested in
//	 * @return An array of IMGame objects representing each game in the team's schedule
//	 */
//	public static IMGame[] getSchedule(int teamId){
//		IMGame[] schedule = null;
//		try {
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			URL url = new URL(DOMAIN + "/api/im/teams/team_schedule/" + teamId);
//			Document dom = db.parse(url.openStream());
//			NodeList nl = dom.getElementsByTagName("im_game");
//			schedule = new IMGame[nl.getLength()];
//			
//			for(int i = 0; i < schedule.length; i++){
//				schedule[i] = createGame((Element)nl.item(i));
//				schedule[i].setOwner(teamId);
//			}
//		} catch (ParserConfigurationException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (UnknownHostException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (IOException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (SAXException e) {
//			Log.e(TAG, e.getMessage());
//		}
//		
//		return schedule;
//	}
//	
//	public static IMStandings getStandings(int divId){
//		DocumentBuilder db;
//		IMStandings standings = null;
//		try {
//			db = dbf.newDocumentBuilder();
//			URL url = new URL(DOMAIN + "/api/im/divisions/standings/" + divId);
//			Document dom = db.parse(url.openStream());
//			
//			NodeList teams = dom.getElementsByTagName("im_team");
//			ArrayList<IMColumn> col = new ArrayList<IMColumn>();
//			for(int i = 0; i < teams.getLength(); i++){
//				NodeList entries = ((Element)teams.item(i)).getElementsByTagName("standing_header");
//				for(int j = 0; j < entries.getLength(); j++){
//					IMColumn column = null;
//					Element entry = (Element)entries.item(j);
//					int rank = getIntValue(entry, "column_sort");
//					
//					if(rank < col.size())
//						column = col.get(rank);
//					
//					if(column == null){
//						column = new IMColumn(rank, getTextValue(entry, "column_display_name"));
//						col.add(rank, column);
//					}
//					
//					column.setRow(i, getTextValue(entry, "column_data"));
//				}
//				
//			}
//			col.trimToSize();
//			standings = new IMStandings(divId);
//			IMColumn[] columns = new IMColumn[col.size()];
//			standings.setColumns(col.toArray(columns));
//		} catch (ParserConfigurationException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (UnknownHostException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (IOException e) {
//			Log.e(TAG, e.getMessage());
//		} catch (SAXException e) {
//			Log.e(TAG, e.getMessage());
//		}
//		
//		return standings;
//	}
//	
//	
//	/** sends the username and password to the API then checks if it validates
//	 *  properly and returns the participant ID if it does or -1 otherwise.
//	 *  @param act The LogIn Activity that is asking for a log in.
//	 *  @param username Username of the participant trying to log in
//	 *  @param password Password of the participant trying to log in
//	 *  @return An IMPlayer object with participant ID filled in or null if failed
//	 */
//	public static void login(LogIn act, String username, String password) {
//		
//		class LoginTask extends AsyncTask<Object, Void, Object[]>{
//			
//			//Your Login code goes here...the username will be at args[1] and the password
//			//will be at args[2]
//			protected Object[] doInBackground(Object...args){
//				//This is the result array
//				Object[] result = new Object[3];
//				
//				//args[0] is the LogIn activity that is asking for the login.  We need to pass this along
//				//in the results so that we can report a successful or failed login.
//				result[0] = args[0];
//				
//				try {
//					/* set up necessary variables*/
//					DocumentBuilder db = dbf.newDocumentBuilder();
//					URL url = new URL(DOMAIN + "/api/system/login?api_key=" + API_KEY);
//					Integer p_id = null;
//					PermLevels perm = null;
//					
//					/* create the xml with the username and password in it*/
//					XmlSerializer serializer = Xml.newSerializer();
//					StringWriter writer = new StringWriter();
//					String xml = null;
//					try {
//						serializer.setOutput(writer);
//						serializer.startDocument("UTF-8", true);
//						serializer.startTag("", "user");
//						serializer.startTag("", "login");
//						serializer.text((String)args[1]);
//						serializer.endTag("", "login");
//						serializer.startTag("", "password");
//						serializer.text((String)args[2]);
//						serializer.endTag("", "password");
//						serializer.endTag("", "user");
//						serializer.endDocument();
//						
//						xml = writer.toString();
//					} catch (Exception e) {
//						Log.e(TAG, e.getMessage());
//					}
//					
//					/* send over the xml with the username and password*/
//					//set up the connection
//					HttpURLConnection con = (HttpURLConnection) url.openConnection();
//					InputStream in = null;
//					OutputStream out;
//					con.setRequestMethod("POST");
//					con.setDoInput(true);
//					con.setDoOutput(true);
//					con.connect();
//					out = con.getOutputStream();
//					
//					//send the file over
//					byte[] xmlBytes = xml.getBytes();
//					out.write(xmlBytes);
//					out.flush();
//					out.close();
//					
//					/** TODO: parse the return xml*/
//					//NodeList part_ids = dom.getElementsByTagName("participant_id");
//					in = con.getInputStream();
//					
//					//put the participants id and permission level in the result array
//					result[1] = p_id;
//					result[2] = perm;
//					
//					
//				} catch (ParserConfigurationException e) {
//					Log.e(TAG, e.getMessage());
//				} catch (UnknownHostException e) {
//					Log.e(TAG, e.getMessage());
//				} catch (IOException e) {
//					Log.e(TAG, e.getMessage());
//				}
//				return result;
//			}
//			//This executes in the main thread after the other method is done
//			protected void onPostExecute(Object[] result){
//				//check if we got a participant id
//				if(result[1] == null){
//					//if no id, report a failed login
//					((LogIn)result[0]).loginFailed();
//				}
//				else{
//					//otherwise, report a successful login
//					((LogIn)result[0]).loginSuccess((Integer)result[1], (PermLevels)result[2]);
//				}
//			}
//			
//		}
//		
//	}
//	
//	
//	
//	private static IMGame createGame(Element e) {
//		IMGame game = null;
//		if(e.getTagName().equals("im_game")){
//			String opponent, bracket, date, time, loc, homeTeam;
//			opponent = getTextValue(e, "opponent");
//			bracket = getTextValue(e, "bracket_name");
//			date = getTextValue(e, "game_date");
//			time = getTextValue(e, "start_time");
//			loc = getTextValue(e, "playing_area_name");
//			homeTeam = getTextValue(e, "home_team");
//			boolean home = (homeTeam.equalsIgnoreCase("true") || homeTeam.equals("1"));
//			
//			game = new IMGame(opponent, bracket, date, time, loc, home);
//			NodeList nl = e.getElementsByTagName("result");
//			if(nl != null && nl.getLength() > 0){
//				Element result = (Element)nl.item(0);
//				String resultString = getTextValue(result, "game_result");
//				int ownerScore = getIntValue(result, "team_score");
//				int oppScore = getIntValue(result, "opponent_score");
//				float ownerSportsmanship = getFloatValue(result, "team_sportsmanship");
//				float oppSportsmanship = getFloatValue(result, "opponent_sportsmanship");
//				
//				game.setResult(resultString, ownerScore, oppScore, ownerSportsmanship, oppSportsmanship);
//			}
//		}
//		return game;
//	}
//	/**
//	 * Used to create a team object from an element in an XML document
//	 * @param e
//	 * @return
//	 */
//	private static IMTeam createTeam(Element e){
//		IMTeam team = null;
//		if(e.getTagName().equals("im_team")){
//			int teamId, leagueId, sportId, divId;
//			String teamName, leagueName, sportName, divName;
//			teamId = getIntValue(e, "id");
//			leagueId = getIntValue(e, "league_id");
//			sportId = getIntValue(e, "sport_id");
//			divId = getIntValue(e, "division_id");
//			teamName = getTextValue(e, "team_name");
//			leagueName = getTextValue(e, "league_name");
//			sportName = getTextValue(e, "sport_name");
//			divName = getTextValue(e, "division_name");
//			
//			team = new IMTeam(teamId, leagueId, sportId, divId, teamName, leagueName, sportName, divName);
//		}
//		return team;
//	}
//	/**
//	 * Used to create a player object from an element in an XML document
//	 * @param e
//	 * @return
//	 */
//	private static IMPlayer createPlayer(Element e){
//		IMPlayer player = null;
//		String tag = e.getTagName();
//		if(tag.equals("im_roster") || tag.equals("captain")){
//			
//			String name, phone, email;
//			
//			name = getTextValue(e, "name");
//			phone = getTextValue(e, "phone");
//			email = getTextValue(e, "email");
//			
//			player = new IMPlayer(name, email, phone);
//		}
//		return player;
//	}
//	
//	/**
//	 * gets the text value held by the tag in a certain element
//	 * @param ele the parent element
//	 * @param tagName the tag name that holds the text data
//	 * @return
//	 */
//	private static String getTextValue(Element ele, String tagName) {
//		String textVal = null;
//		NodeList nl = ele.getElementsByTagName(tagName);
//		if(nl != null && nl.getLength() > 0) {
//			Element el = (Element)nl.item(0);
//			Node n = el.getFirstChild();
//			if(n != null)
//				textVal = el.getFirstChild().getNodeValue();
//		}
//
//		return textVal;
//	}
//	
//	/**
//	 * Just like getTextValue but for ints
//	 * @param ele
//	 * @param tagName
//	 * @return
//	 */
//	private static int getIntValue(Element ele, String tagName) {
//		
//		return Integer.parseInt(getTextValue(ele,tagName));
//	}
//	
//	/**
//	 * gets a variable number of text values with a given tag name from an element in an xml document
//	 * @param ele
//	 * @param tagName
//	 * @return
//	 */
//	private static String[] getTextValues(Element ele, String tagName){
//		String textVals[] = null;
//		NodeList nl = ele.getElementsByTagName(tagName);
//		if(nl != null && nl.getLength() > 0) {
//			textVals = new String[nl.getLength()];
//			for(int i = 0; i < textVals.length; i++){
//				Element el = (Element)nl.item(i);
//				textVals[i] = el.getFirstChild().getNodeValue();
//			}
//		}
//
//		return textVals;
//	}
//	
//	private static float getFloatValue(Element ele, String tagName){
//		return Float.parseFloat(getTextValue(ele, tagName));
//	}
//	
	
}

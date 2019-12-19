/*******************************************************************************
 *
 *   Redistribution, modification and use of this software are permitted under
 *   terms of the Apache 2.0 License.
 *
 *   This software is distributed in the hope that it will be useful,
 *   but WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; without even the implied
 *   warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   Apache 2.0 License for more details.
 *
 *   You should have received a copy of the Apache 2.0 License along with    
 *   this software. If not, see <http://www.apache.org/licenses/>.
 *   
 *  Copyright (c) 2002 JSON.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, merge, 
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 *   
 *  The Software shall be used for Good, not Evil.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *   
 * WordNet 3.0 Copyright 2006 by Princeton University. All rights reserved. THIS SOFTWARE AND DATABASE IS 
 * PROVIDED "AS IS" AND PRINCETON UNIVERSITY MAKES NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED. 
 * BY WAY OF EXAMPLE, BUT NOT LIMITATION, PRINCETON UNIVERSITY MAKES NO REPRESENTATIONS OR WARRANTIES OF 
 * MERCHANT- ABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE USE OF THE LICENSED SOFTWARE, 
 * DATABASE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 * The name of Princeton University or Princeton may not be used in advertising or publicity pertaining
 * to distribution of the software and/or database. Title to copyright in this software, database and any 
 * associated documentation shall at all times remain with Princeton University and LICENSEE agrees to preserve same.
 * 
 * 
 * 
 * 
 * 
 */



public class Main {

	public static void main(String[] args) {
		
		JsonCreatorSPA jsonCreatorSpa = new JsonCreatorSPA();
		System.out.println("SPA_nouns");
		jsonCreatorSpa.nounJson();
		System.out.println("SPA_adjectives");
		jsonCreatorSpa.adjJson();
		System.out.println("SPA_verb");
		jsonCreatorSpa.verbJson();
		System.out.println("SPA_Adverbs");
		jsonCreatorSpa.advJson();

		JsonCreatorENG jsonCreatorEng = new JsonCreatorENG();
		System.out.println("ENG_nouns");
		jsonCreatorEng.nounJson();
		System.out.println("ENG_adjectives");
		jsonCreatorEng.adjJson();
		System.out.println("ENG_verbs");
		jsonCreatorEng.verbJson();
		System.out.println("ENG_adverbs");
		jsonCreatorEng.advJson();
	}

}

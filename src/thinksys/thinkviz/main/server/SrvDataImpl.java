/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package thinksys.thinkviz.main.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.taskdefs.SQLExec.DelimiterType;

import thinksys.thinkviz.main.client.SrvData;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SrvDataImpl extends RemoteServiceServlet implements SrvData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4539926974695279742L;

	@Override
	public String[][] getData() {
		String csvFile = "C:\\Users\\u409397\\workspace\\ThinkViz\\src\\thinksys\\thinkviz\\main\\public\\KResults.txt";
		BufferedReader br = null;
		String line = "";
		String delim = ",";
		List<String[]> lst = new ArrayList<>();
		try {
			 
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				lst.add(line.split(delim));
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String[][] data = new String[lst.size()][];
		for(int i=0; i<lst.size(); i++){
			data[i] = lst.get(i);
		}
	 
		return data;
	}
}

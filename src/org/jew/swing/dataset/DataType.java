/*
Copyright (C) 2013 by Florian SIMON

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package org.jew.swing.dataset;


import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jew.swing.exception.MalFormattedRegExException;


public class DataType 
{
	public static final DataType BOOLEAN = new DataType(Boolean.class);

	public static final DataType TEXT = new DataType(String.class);

	public static final DataType NUMBER = new DataType(Double.class);
	
	protected Map<Object, String> dico;
	
	public Map<Object, String> getDico(){
		return this.dico;
	}

	protected Format format;

	protected Class<?> cellType;

	/**
	 * Get accessor for cellType
	 * @return  value of cellType
	 */
	public Class<?> getCellType () {
		return this.cellType;
	}


	public DataType(
			final Class<?> cellType)
	{
		this.cellType = cellType;
	}

	public DataType(
			final Class<?> cellType,
			final Format format)
	{

		this(cellType);


		this.format = format;

	}

	public DataType(
			final Class<?> cellType,
			final Map<Object, String> dico)
	{

		this(cellType);


		this.dico = dico;

	}

	public static DataType TEXT(
			final Map dico){

		return new DataType(Object.class, dico);
	}
	
	public static DataType LIST(
			final Map dico){

		return new DataType(List.class, dico);
	}

	public Object format(
			final Object obj)
	{
		if(this.format != null){
			return this.format.format(obj);
		}else if(this.dico != null){
			return String.valueOf(this.dico.get(obj));
		}else{
			return String.valueOf(obj);
		}		
	}

	public static DataType NUMBER(
			final String regex){

		if(! regex.matches("n*N+(.N+n*)?")){
			try {
				throw new MalFormattedRegExException(regex);				
			} catch (MalFormattedRegExException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		int minDigits = 0;
		int maxDigits = 0;		
		int minFraction = 0;
		int maxFraction = 0;

		if(regex.matches("n*N+.N+n*")){
			String[] tab = regex.split("\\.");
			String digitReg = tab[0];
			String fractionReg = tab[1];

			minDigits = digitReg.replaceAll("n", "").length();
			maxDigits = digitReg.length();			
			minFraction = fractionReg.replaceAll("n", "").length();
			maxFraction = fractionReg.length();
		}else{
			minDigits = regex.replaceAll("n", "").length();
			maxDigits = regex.length();			
			minFraction = 0;
			maxFraction = 0;
		}				

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		numberFormat.setGroupingUsed(false);
		numberFormat.setMinimumIntegerDigits(minDigits);
		numberFormat.setMaximumIntegerDigits(maxDigits);
		numberFormat.setMinimumFractionDigits(minFraction);
		numberFormat.setMaximumFractionDigits(maxFraction);

		return new DataType(Double.class, numberFormat);
	}
}

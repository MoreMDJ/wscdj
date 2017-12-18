package com.zxsm.wsc.common.UtilsTools;

import java.util.ArrayList;
import java.util.Collections;

public class DjArrayUtils {
	
	/**
	 * 交集
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static ArrayList<Long> Intersection(Long[] a1,Long[] a2){
		ArrayList<Long> list=new ArrayList<Long>();
		for(int i=0;i<a1.length;i++)
			for(int j=0;j<a2.length;j++)
				if(a1[i].equals(a2[j]))
					list.add(a2[j]);
		return list;
	}

	/**
	 * 和集
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static ArrayList<Long> Union(Long[] a1,Long[] a2){
		ArrayList<Long> list1=new ArrayList<Long>();
		ArrayList<Long> list2=new ArrayList<Long>();
		for(Long i:a1)
			list1.add(i);
		for(Long i:a2)
			list2.add(i);
		list1.removeAll(list2);
		list2.addAll(list1);
		return list2;
	}
	/**
	 * 补集
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static ArrayList<Long> complement(Long[] a1,Long[] a2){
		ArrayList<Long> list1=new ArrayList<Long>();
		ArrayList<Long> list2=new ArrayList<Long>();
		for(Long i:a1)
			list1.add(i);
		for(Long i:a2)
			list2.add(i);
		list1.removeAll(list2);
		return list1;
	}

	public static void OutPut(ArrayList<Long> list){
		Collections.sort(list);
		if(list.size()==0)
			System.out.println("null");
		else
			for(Long i:list)
				System.out.print(i +" ");
		System.out.println("\n");
	}

}

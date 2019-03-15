//package com.geobigdata.ESInterfaceAppTest;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///*
// * @author pjl
// * @version 创建时间：2019年1月11日 下午12:55:41
// * 类说明
// */
//public class pooltest {
//	private ExecutorService executor = Executors.newFixedThreadPool(3);
//
//	public int refresh() {
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("running");
//				try {
//					Thread.sleep(2000);
//					System.out.println("done");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		return 1;
//	}	
//	public void refresh2() {
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("running2");
//				try {
//					Thread.sleep(2000);
//					System.out.println("done2");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	
//	public static void main(String[] args) {
////		pooltest a = new pooltest();
////		int[] b= {0,0,0,0,0};
////		for (int i = 0; i < 5; ++i) {
////			b[i]=a.refresh();
////		}
////		a.refresh2();
//		
//	}
//}

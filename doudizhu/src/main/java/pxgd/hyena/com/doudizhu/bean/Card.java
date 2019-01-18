package pxgd.hyena.com.doudizhu.bean;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Card  implements Comparable<Card> {
	
	private String cid;//���ı��
	private int name; //Card������  3\4\...K��A(14) 2(15)��С��(16)������(17)
	
	private int x=0;      //�����ƺ�����
	private int y=0;	  //������������
	
	//�����ֻ�������
	private int unx;  
	private int uny; 
	
	//��ͼ���������
	private int ulx;
	private int uly;
	
	//�����ֻ�������
	private int dnx;
	private int dny;
	
	//��ͼ���������
	private int dlx;
	private int dly;
	
	private int sw;//ʵ��ռ���
	
	private int width;    //�˿��ƿ��
	private int height;   //�˿��Ƹ߶�
	
	private Bitmap numbitmap;//����ͼƬ
	private Bitmap logobitmap;//ͼ��ͼƬ
	
	private boolean clicked=false;//�Ƿ�ѡ��
	
	public Card(Bitmap numbitmap, Bitmap logobitmap,String cid, int name) {
		this.numbitmap = numbitmap;
		this.logobitmap = logobitmap;
		this.cid=cid;
		this.name = name;
	}

	/**
	 * ������Ӧ����
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param sw
	 */
	public void setLocationAndSize(int x,int y,int w,int h,int sw){
		this.x=x;
		this.y=y;
		this.width=w;
		this.height=h;
		this.sw=sw;
		
		this.unx=x+10;
		this.uny=y+10;
		this.ulx=x+10;
		this.uly=y+numbitmap.getHeight()+20;
		
		this.dnx=x+w-numbitmap.getWidth()-10;
		this.dny=y+h-numbitmap.getHeight()-10;
		this.dlx=x+w-logobitmap.getWidth()-10;
		this.dly=y+h-numbitmap.getHeight()-logobitmap.getWidth()-20;
	}
	
	
	
	public int getUnx() {
		return unx;
	}


	public void setUnx(int unx) {
		this.unx = unx;
	}


	public int getUny() {
		return uny;
	}


	public void setUny(int uny) {
		this.uny = uny;
	}


	public int getUlx() {
		return ulx;
	}


	public void setUlx(int ulx) {
		this.ulx = ulx;
	}


	public int getUly() {
		return uly;
	}


	public void setUly(int uly) {
		this.uly = uly;
	}


	public int getDnx() {
		return dnx;
	}


	public void setDnx(int dnx) {
		this.dnx = dnx;
	}


	public int getDny() {
		return dny;
	}


	public void setDny(int dny) {
		this.dny = dny;
	}


	public int getDlx() {
		return dlx;
	}


	public void setDlx(int dlx) {
		this.dlx = dlx;
	}


	public int getDly() {
		return dly;
	}


	public void setDly(int dly) {
		this.dly = dly;
	}


	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Bitmap getNumbitmap() {
		return numbitmap;
	}

	public Bitmap getLogobitmap() {
		return logobitmap;
	}

	public int getName() {
		return name;
	}

	
	public String getCid() {
		return cid;
	}

	public int getSw() {
		return sw;
	}


	@Override
	public int compareTo(Card another) {
		return another.name-this.name;
	}
}

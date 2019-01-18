package pxgd.hyena.com.doudizhu.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

	private int pid;//��ұ��
	private boolean dizhuflag;//�Ƿ��ǵ���
	private GameGrab status;//��������̬
	private int currself;//��ǰ�������ı���
	private boolean person;//�ǲ����� ��true������ false
	private List<Card> cards=new ArrayList<Card>();//��ǰ�������
	private List<Card> outcards=new ArrayList<Card>();//ÿ�γ���
	private boolean play;//�Ƿ���ƻ��߸���
	
	public Player(int pid,boolean person){
		this.pid=pid;
		this.person=person;
		init();
	}
	
	public void init(){
		this.currself=0;
		this.dizhuflag=false;
		this.status=GameGrab.none;
		this.play=false;
		clear();
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public GameGrab getStatus() {
		return status;
	}
	public void setStatus(GameGrab status) {
		this.status = status;
	}
	public boolean isDizhuflag() {
		return dizhuflag;
	}
	public void setDizhuflag(boolean dizhuflag) {
		this.dizhuflag = dizhuflag;
	}

	public List<Card> getCards() {
		return cards;
	}

	/**
	 * ���
	 * @param card
	 */
	public void addCards(Card card) {
		this.cards.add(card);
	}
	/**
	 * �Ƴ�
	 * @return
	 */
	public void removeCards(List<Card> outcards){
		this.cards.removeAll(outcards);
	}
	

	public List<Card> getOutcards() {
		return outcards;
	}

	/**
	 * ���Ƽ�¼��
	 * @param outcard
	 */
	public void addOutcards(Card outcard) {
		this.outcards.add(outcard);
	}
	
	public int getCurrself() {
		return currself;
	}

	public void setCurrself(int currself) {
		this.currself = currself;
	}

	public boolean isPerson() {
		return person;
	}

	/**
	 * �������������
	 */
	public void clear(){
		this.cards.clear();
		this.outcards.clear();
	}
	/**
	 * ��ճ��Ƽ���
	 */
	public void clearOut(){
		this.outcards.clear();
	}
	/**
	 * ����
	 */
	public void sort(){
		Collections.sort(this.cards);
	}
	
	/**
	 * ��������
	 */
	public void outSort(){
		Collections.sort(this.outcards);
	}
	/**
	 * ��ǰ�Ƶ���Ŀ
	 * @return
	 */
	public int size(){
		return this.cards.size();
	}
	
	public int outSize(){
		return this.outcards.size();
	}
	
	/**
	 * ���ĳ����
	 * @param index
	 * @return
	 */
	public Card getCard(int index){
		return this.cards.get(index);
	}
	
	public Card getOutCard(int index){
		return this.outcards.get(index);
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}
	
	
}

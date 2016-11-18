package com.example.unique.mybeatplane.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.unique.mybeatplane.R;
import com.example.unique.mybeatplane.factory.GameObjectFactory;
import com.example.unique.mybeatplane.interfaces.IMyPlane;
import com.example.unique.mybeatplane.view.MainView;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;

/*玩家飞机的类*/
public class MyPlane extends GameObject implements IMyPlane{
	private float middle_x;			 // 飞机的中心坐标
	private float middle_y;
	private long startTime;	 	 	 // 开始的时间
	private long endTime;	 	 	 // 结束的时间
	private boolean isChangeBullet;  // 标记更换了子弹
	private Bitmap myplane000;			 // 飞机飞行时的图片
	private Bitmap myplane;
	private Bitmap myplane2;		 // 飞机爆炸时的图片
	private Bitmap myplane3;
	private List<Bullet> bullets;	 // 子弹的序列
	private MainView mainView;
	private GameObjectFactory factory;
	private int score=100;
	public MyPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.speed = 8;
		this.score = 100;
		isChangeBullet = false;
		factory = new GameObjectFactory();
//		bullets = new ArrayList<Bullet>();
//		changeButtle();
	}
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	// 设置屏幕宽度和高度
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width/2;
		middle_y = object_y + object_height/2;
	}
	// 初始化图片资源的
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		myplane000 = BitmapFactory.decodeResource(resources, R.drawable.fishself);
		myplane = myplane000;
		myplane2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
		myplane3 = BitmapFactory.decodeResource(resources, R.drawable.fishself);
		object_width = myplane.getWidth() ; // 获得每一帧位图的宽
		object_height = myplane.getHeight(); 	// 获得每一帧位图的高
	}

/*	//变大的时候
    public void bigger(){
		Matrix matrix = new Matrix();
		matrix.postScale((float) 1.2, (float) 1.2);
		object_width *=1.2;
		object_height *=1.2;

		myplane, 0, 0, object_width, object_height, matrix, false
		,0,object_width, object_height, matrix, false

		Bitmap bitmap2 = createBitmap();
		myplane=bitmap2;

	}
*/


	// 对象的绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的X坐标
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myplane, object_x - x, object_y, paint);
		}
		else{
			int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的Y坐标
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
		}
	}


	public void drawSelf2(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			object_width=object_width*1.2f;
			object_height=object_height*1.2f;
			int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的X坐标
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			Bitmap dragimg=Bitmap.createScaledBitmap(myplane3, (int)(object_width), (int)(object_height), true);
			myplane=dragimg;
			canvas.drawBitmap(myplane, object_x - x, object_y, paint);

			/*canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myplane, object_x - x, object_y, paint);
			canvas.restore();
			* */
		}
		else{
			int x = (int) (currentFrame * object_width); // 获得当前帧相对于位图的Y坐标
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 1;
			}
		}
	}


	// 释放资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
/*		for(Bullet obj:bullets){
			obj.release();
		}
*/
		if(!myplane.isRecycled()){
			myplane.recycle();
		}
		if(!myplane2.isRecycled()){
			myplane2.recycle();
		}
	}
	//发射子弹
	@Override
	public void shoot(Canvas canvas,List<EnemyPlane> planes) {
		// TODO Auto-generated method stub
		//遍历子弹的对象
		for(Bullet obj:bullets){
			if(obj.isAlive()){		//子弹是否为存活状态
				for(EnemyPlane pobj:planes){ //遍历敌机对象
					// 判断敌机是否被检测碰撞
					if( pobj.isCanCollide()){
						if(obj.isCollide((GameObject)pobj)){			   		   // 检查碰撞
							pobj.attacked(obj.getHarm());		   // 敌机增加伤害
							if(pobj.isExplosion()){
								mainView.addGameScore(pobj.getScore());// 获得分数
								if(pobj instanceof SmallPlane){
									mainView.playSound(2);
								}
								else if(pobj instanceof MiddlePlane){
									mainView.playSound(3);
								}
								else if(pobj instanceof BigPlane){
									mainView.playSound(4);
								}
								else{
									mainView.playSound(5);
								}
							}
							break;
						}
					}
				}
				obj.drawSelf(canvas);					//绘制子弹
			}
		}
	}

	//初始化子弹
	@Override
	public void initButtle() {
		// TODO Auto-generated method stub
		for(Bullet obj:bullets){
			if(!obj.isAlive()){
				obj.initial(0,middle_x, middle_y);
				break;
			}
		}
	}

	//更换子弹
	@Override
	public void changeButtle() {
		// TODO Auto-generated method stub
//		bullets.clear();
		if(isChangeBullet){
			for(int i = 0;i < 4;i++){
				MyBullet2 bullet = (MyBullet2) factory.createMyBullet2(resources);
				bullets.add(bullet);
			}
		}
		else{
			for(int i = 0;i < 4;i++){
				MyBullet bullet = (MyBullet) factory.createMyBullet(resources);
				bullets.add(bullet);
			}
		}
	}
	//判断子弹是否超时
	public void isBulletOverTime(){
		if(isChangeBullet){
			endTime = System.currentTimeMillis();
			if(endTime - startTime > 15000){
				isChangeBullet = false;
				startTime = 0;
				endTime = 0;
				changeButtle();
			}
		}
	}
	//getter和setter方法
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Override
	public boolean isChangeBullet() {
		return isChangeBullet;
	}
	@Override
	public void setChangeBullet(boolean isChangeBullet) {
		this.isChangeBullet = isChangeBullet;
	}
	@Override
	public float getMiddle_x() {
		return middle_x;
	}
	@Override
	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}
	@Override
	public float getMiddle_y() {
		return middle_y;
	}
	@Override
	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}

	public int getScore(){
		return score;
	}


}

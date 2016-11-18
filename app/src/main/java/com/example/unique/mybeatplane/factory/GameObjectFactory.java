package com.example.unique.mybeatplane.factory;

import android.content.res.Resources;

import com.example.unique.mybeatplane.object.BigPlane;
import com.example.unique.mybeatplane.object.BossBullet;
import com.example.unique.mybeatplane.object.BossPlane;
import com.example.unique.mybeatplane.object.MyBullet;
import com.example.unique.mybeatplane.object.BulletGoods;
import com.example.unique.mybeatplane.object.GameObject;
import com.example.unique.mybeatplane.object.MiddlePlane;
import com.example.unique.mybeatplane.object.MissileGoods;
import com.example.unique.mybeatplane.object.MyBullet2;
import com.example.unique.mybeatplane.object.MyPlane;
import com.example.unique.mybeatplane.object.SmallPlane;
/*游戏对象的工厂类*/
public class GameObjectFactory {
	//创建小型敌机的方法
	public GameObject createSmallPlane(Resources resources){
		return new SmallPlane(resources);
	}
	//创建中型敌机的方法
	public GameObject createMiddlePlane(Resources resources){
		return new MiddlePlane(resources);
	}
	//创建大型敌机的方法
	public GameObject createBigPlane(Resources resources){
		return new BigPlane(resources);
	}
	//创建BOSS敌机的方法
	public GameObject createBossPlane(Resources resources){
		return new BossPlane(resources);
	}
	//创建玩家飞机的方法
	public GameObject createMyPlane(Resources resources){
		return new MyPlane(resources);
	}
	//创建玩家子弹
	public GameObject createMyBullet(Resources resources){
		return new MyBullet(resources);
	}
	//创建玩家子弹
	public GameObject createMyBullet2(Resources resources){
		return new MyBullet2(resources);
	}
	//创建BOSS子弹
	public GameObject createBossBullet(Resources resources){
		return new BossBullet(resources);
	}
	//创建导弹物品
	public GameObject createMissileGoods(Resources resources){
		return new MissileGoods(resources);
	}
	//创建子弹物品
	public GameObject createBulletGoods(Resources resources){
		return new BulletGoods(resources);
	}
}
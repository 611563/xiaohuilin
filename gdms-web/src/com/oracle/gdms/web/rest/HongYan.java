package com.oracle.gdms.web.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.oracle.gdms.entity.GoodsEntity;
import com.oracle.gdms.entity.GoodsModel;
import com.oracle.gdms.entity.GoodsType;
import com.oracle.gdms.entity.GoodsTypeEntity;
import com.oracle.gdms.entity.ResponseEntity;
import com.oracle.gdms.service.GoodsService;

import com.oracle.gdms.util.Factory;

@Path("/hongyan")
public class HongYan {
	
	@Path("/sing")
	@GET
	public String sing() {
		System.out.println("泽洲带我们飞");
		return "hello";
	}
	
	@Path("/sing/{name}")
	@GET
	public String sing(@PathParam("name") String name) {
		System.out.println(name);
		return "OK";
	}
	
	@Path("/sing/ci")   //rest/hongyan/sing/ci?name=xxxxxx
	@GET
	public String singOne(@QueryParam("name") String name) {
		System.out.println(name);
		return "CI";
	}
	
	@Path("/push/one")
	@POST
	public String push(@FormParam("name") String name,@FormParam("id") String id) {
		
		System.out.println(name);
		System.out.println(id);
		return "Form";
	}
	
	@Path("/push/json")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String pushjson(String jsonparam) {
		System.out.println(jsonparam);
		JSONObject j = JSONObject.parseObject(jsonparam);
		String name = j.getString("name");
		String lei = j.getString("lei");
		String age = j.getString("age");
		System.out.println("name=" +name);
		System.out.println("lei=" +lei);
		System.out.println("age=" +age);
		
		return "json";
	}
	
	@Path("/goods/update/type")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果是json
	public String updateGoodsType(String jsonparam) {
		JSONObject j = JSONObject.parseObject(jsonparam);
		String goodsid = j.getString("goodsid");
		String gtid = j.getString("gtid");
		System.out.println(jsonparam);
		System.out.println(goodsid);
		System.out.println(gtid);
		GoodsEntity goods = new GoodsEntity();
		goods.setGoodsid(Integer.parseInt(goodsid));
		goods.setGtid(Integer.parseInt(gtid));
		
		GoodsService service = (GoodsService)Factory.getInstance().getObject("goods.service.impl");
		int count = service.updateGoodsType(goods);
		if (count > 0) {
			j.put("code", 0);
			j.put("msg", "更新成功");
		}else {
			j.put("code", 1005);
			j.put("msg", "更新商品失败");
		}
		return j.toJSONString();
	}
	
	@Path("/goods/chaxun/type")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果是json
	public List<GoodsModel> chaxunGoodsTypeAll(String jsonparam) {
		JSONObject j = JSONObject.parseObject(jsonparam);
		String gtid1 = j.getString("gtid");
		System.out.println(jsonparam);
		System.out.println(gtid1);
		int gtid = Integer.parseInt(gtid1);
		GoodsService service = (GoodsService)Factory.getInstance().getObject("goods.service.impl");
		List<GoodsModel> list = service.chaxunGoodsTypeAll(gtid);
		System.out.println("size ="+list.size());
		return list;
	}
	
	@Path("/goods/push/bytype")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果是json
	public List<GoodsModel> findByType(GoodsType type) {
		List<GoodsModel> list= null;
		GoodsService service = (GoodsService)Factory.getInstance().getObject("goods.service.impl");
		list = service.findByType(type.getGtid());
		System.out.println("size ="+list.size());
		return list;
	}
	
	@Path("/goods/push/tuisong")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果是json
	public ResponseEntity pushGoodsOne(String jsonparam) {
		ResponseEntity r = new ResponseEntity();
		
		try {
			JSONObject a = JSONObject.parseObject(jsonparam);
			String gs = a.getString("goods");
			GoodsModel goods = JSONObject.parseObject(gs,GoodsModel.class);
			System.out.println("商品ID="+goods.getGoodsid());
			System.out.println("商品名称="+goods.getName());
			r.setCode(0);
			r.setMessage("推送成功");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			r.setCode(1005);
			r.setMessage("推送失败"+jsonparam);
		}
		return r;		
	}
}

package com.stylefeng.guns.rest.modular.vo;

/**
 * Created by liutj on 2019/3/5.
 */
public class ResponseVO<M> {
    //返回状态
    private int status;
    //返回信息
    private String message;
    //返回数据实体
    private M data;
    //图片前缀
    private String imgPre;
    //当前页
    private int nowPage;
    //总页数
    private int totalPage ;

    private ResponseVO(){

    }

    public static<M> ResponseVO success(M data){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(data);
        responseVO.setStatus(0);
        return responseVO;
    }

    public static<M> ResponseVO success(M data, String imgPre){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        responseVO.setStatus(0);
        return responseVO;
    }

    public static<M> ResponseVO success(M data,int nowPage ,int totalPage){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(data);
        responseVO.setStatus(0);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }

    public static<M> ResponseVO success(M data, String imgPre,int nowPage ,int totalPage){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        responseVO.setStatus(0);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }

    public static<M> ResponseVO success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setMessage(msg);
        responseVO.setStatus(0);
        return responseVO;
    }

    public static<M> ResponseVO serviceFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setMessage(msg);
        responseVO.setStatus(1);
        return responseVO;
    }

    public static<M> ResponseVO appFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setMessage(msg);
        responseVO.setStatus(999);
        return responseVO;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}

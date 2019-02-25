package com.example.twitter;

/**
 * Created by hasee on 2018/12/13.
 */

public class Info {

    //头像
    public String _imghead;
    // 名字
    public String _name;
    // 账号
    public String _idnumber;
    // 发言时间
    public String _wordTime;
    // 内容
    public String _content;
    //评论数 cmt
    public String _ctm;
    //转发数 tn
    public String _tn;
    //点赞数 lk
    public String _lk;

    //转发 多4项
    //名字2
    public String _name2;
    //账号2
    public String _idnumber2;
    //发言时间2
    public String _wordTime2;
    //内容2
    public String _content2;

    //推文Id
    public int articleId;
    public String fan;
    public String follow;
    //public int origin;
    public String pic;
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    //类型   是否是转发的 0是原创 不是0则为转发的推文
    public int type;

    public Info(){
    }

    public Info(String _imghead, String _name, String _idnumber, String _wordTime, String _content){
        //   this._imgId = _imgId;
        this._imghead = _imghead;
        this._name = _name;
        this._idnumber = _idnumber;
        this._wordTime = _wordTime;
        this._content = _content;

    }

    public Info(String _imghead, String _name, String _idnumber, String _wordTime, String _content,
                String _ctm, String _tn, String _lk){
        //   this._imgId = _imgId;
        this._imghead = _imghead;
        this._name = _name;
        this._idnumber = _idnumber;
        this._wordTime = _wordTime;
        this._content = _content;
        this._ctm = _ctm;
        this._tn = _tn;
        this._lk = _lk;
    }
    public Info(String _imghead, String _name, String _idnumber, String _wordTime, String _content,
                String _ctm, String _tn, String _lk, String _name2, String _idnumber2, String _wordTime2, String _content2){
        //   this._imgId = _imgId;
        this._imghead = _imghead;
        this._name = _name;
        this._idnumber = _idnumber;
        this._wordTime = _wordTime;
        this._content = _content;
        this._ctm = _ctm;
        this._tn = _tn;
        this._lk = _lk;
        this._name2 = _name2;
        this._idnumber2 = _idnumber2;
        this._wordTime2 = _wordTime2;
        this._content2 = _content2;
    }
    public String get_imghead() {
        return _imghead;
    }
    public void set_imghead(String _imghead) {
        this._imghead = _imghead;
    }
    public String get_name() {
        return _name;
    }
    public void set_name(String _name) {
        this._name = _name;
    }
    public String get_idnumber() {
        return _idnumber;
    }
    public void set_idnumber(String _idnumber) {
        this._idnumber = _idnumber;
    }
    public String get_wordTime() {
        return _wordTime;
    }
    public void set_wordTime(String _wordTime) {
        this._wordTime = _wordTime;
    }
    public String get_content() {
        return _content;
    }
    public void set_content(String _content) {
        this._content = _content;
    }
    public String get_ctm() {
        return _ctm;
    }
    public void set_ctm(String _ctm) {
        this._ctm = _ctm;
    }
    public String get_tn() {
        return _tn;
    }
    public void set_tn(String _tn) {
        this._tn = _tn;
    }
    public String get_lk() {
        return _lk;
    }
    public void set_lk(String _lk) {
        this._lk = _lk;
    }
    public String get_name2() {
        return _name2;
    }
    public void set_name2(String _name2) {
        this._name2 = _name2;
    }
    public String get_idnumber2() {
        return _idnumber2;
    }
    public void set_idnumber2(String _idnumber2) {
        this._idnumber2 = _idnumber2;
    }
    public String get_wordTime2() {
        return _wordTime2;
    }
    public void set_wordTime2(String _wordTime2) {
        this._wordTime2 = _wordTime2;
    }
    public String get_content2() {
        return _content2;
    }
    public void set_content2(String _content2) {
        this._content2 = _content2;
    }
    public int getArticleId() {
        return articleId;
    }
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getFan() {
        return fan;
    }
    public void setFan(String fan) {
        this.fan = fan;
    }
    public String getFollow() {
        return follow;
    }
    public void setFollow(String follow) {
        this.follow = follow;
    }
}

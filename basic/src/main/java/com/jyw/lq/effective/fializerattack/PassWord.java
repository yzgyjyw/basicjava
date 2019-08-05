package com.jyw.lq.effective.fializerattack;

//假设这个类不是final修饰的，允许继承
public class PassWord {
    private String passwd;

    public PassWord(String passwd) {
        if (!checkPassWd(passwd)) {
            throw new IllegalArgumentException("passwd incorret");
        }
        this.passwd = passwd;
    }

    public boolean checkPassWd(String passwd) {
        return "123".equals(passwd);
    }

    public void action(){
        System.out.println("已经获取密码,开始干活");
    }

    /*//解决方法:在父类中重写一个空的finalize方法，不过加上final修饰符使其不能被继承
    @Override
    protected final void finalize() throws Throwable {
        super.finalize();
    }*/

    public static void main(String[] args) {
        /*PassWord passWord;
        try{
            passWord = new PassWord("abc");
        }catch (Exception e){
            e.printStackTrace();
        }*/

        /*System.gc();*/
//        passWord.action();

        PassWordAttack passWordAttack;
        try{
            passWordAttack = new PassWordAttack("abc");
        }catch (Exception e){}
        System.gc();
        //确保fializer运行
        System.runFinalization();
        PassWordAttack.instance.action();
    }
}

class PassWordAttack extends PassWord{

    public static PassWordAttack instance;

    public PassWordAttack(String passwd) {
        super(passwd);
    }

    @Override
    protected void finalize() throws Throwable {
        instance = this;
    }
}



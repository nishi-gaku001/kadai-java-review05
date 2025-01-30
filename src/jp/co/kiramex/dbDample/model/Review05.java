package jp.co.kiramex.dbDample.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // 変数宣言
        Connection con = null;  // DB用
        PreparedStatement spstmt = null;    //検索用
        ResultSet rs =null; //結果表示用

        try {
            // ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true", "root",
                    "seihoku6890");

            // DBとやりとりする窓口（ここでPreparedStatementを使うはず）
            String select_sql = "SELECT * FROM kadaidb.person WHERE id =?";
            spstmt = con.prepareStatement(select_sql);

            // 検索するidを入力（int型に変換する。これでOkか？）
            System.out.println("検索キーワードを入力してください > ");
            int num1 = keyInNum();

            // 入力されたidをPreparedStatementオブジェクトにセット
            spstmt.setInt(1,num1);

            // select文の実行と結果を格納/代入
            rs = spstmt.executeQuery();

            // 入力されたidで検索
            while(rs.next()) {
                // name列の値を取得
                String name = rs.getString("name");

                // age列の値を取得
                int age = rs.getInt("age");

                //　取得した値を表示
                System.out.println(name + "\n" + age);
            }


        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 接続を閉じる

            // rsを閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }

            // spstmtを閉じる
            if (spstmt != null) {
                try {
                    spstmt.close();
                } catch (Exception e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }

            // 最後にconを閉じる
            if ( con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    // 検索用IDをString型で返す
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (Exception e) {
        }
        return line;
    }

    // 検索用idをint型で返す
    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
        }
        return result;
    }
}

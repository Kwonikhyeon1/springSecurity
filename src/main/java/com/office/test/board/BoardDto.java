package com.office.test.board;

import lombok.Data;

@Data
public class BoardDto {
   private int b_no;
   private String b_owner_id;
   private String b_title;
   private String b_body;
   private int b_hit;
   private int b_step;
   private int b_indent;
   private String b_reg_date;
   private String b_mod_date;
}

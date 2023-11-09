ALTER TABLE "public"."ext_act_process_form"
  ADD COLUMN "circulate" bool;

COMMENT ON COLUMN "public"."ext_act_process_form"."circulate" IS '流程是否可以循环发起';
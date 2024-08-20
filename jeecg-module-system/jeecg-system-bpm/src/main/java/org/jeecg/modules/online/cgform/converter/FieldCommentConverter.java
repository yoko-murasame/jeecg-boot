package org.jeecg.modules.online.cgform.converter;

import java.util.Map;

public abstract interface FieldCommentConverter
{
  public abstract String converterToVal(String paramString);

  public abstract String converterToTxt(String paramString);

  public abstract Map<String, String> getConfig();
}

/* Location:           C:\Users\tx\Desktop\hibernate-common-ol-5.4.74(2).jar
 * Qualified Name:     org.jeecg.modules.online.cgform.converter.FieldCommentConverter
 * JD-Core Version:    0.6.2
 */
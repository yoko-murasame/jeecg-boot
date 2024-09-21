//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.bpm.d.a;

import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class e {
    public static final int a = 0;
    public static final int b = 0;
    private static List<String> c = new ArrayList();
    private static List<String> d = new ArrayList();
    private static List<String> e = new ArrayList();
    private static List<String> f = new ArrayList();
    private static Color g;
    private static Color h;
    private static Color i;
    private static Stroke j;
    private int k;
    private int l;

    public e() {
        a();
    }

    protected static void a() {
        c.add("manualTask");
        c.add("receiveTask");
        c.add("scriptTask");
        c.add("sendTask");
        c.add("serviceTask");
        c.add("userTask");
        e.add("exclusiveGateway");
        e.add("inclusiveGateway");
        e.add("eventBasedGateway");
        e.add("parallelGateway");
        d.add("intermediateTimer");
        d.add("intermediateMessageCatch");
        d.add("intermediateSignalCatch");
        d.add("intermediateSignalThrow");
        d.add("messageStartEvent");
        d.add("startTimerEvent");
        d.add("error");
        d.add("startEvent");
        d.add("errorEndEvent");
        d.add("endEvent");
        f.add("subProcess");
        f.add("callActivity");
    }

    public InputStream a(String var1) throws IOException {
        HistoricProcessInstanceEntity var2 = Context.getCommandContext().getHistoricProcessInstanceEntityManager().findHistoricProcessInstance(var1);
        String var3 = var2.getProcessDefinitionId();
        GetBpmnModelCmd var4 = new GetBpmnModelCmd(var3);
        BpmnModel var5 = var4.execute(Context.getCommandContext());
        this.a(var5);
        this.k = 0;
        this.l = 0;
        this.k = this.k <= 5 ? 5 : this.k;
        this.l = this.l <= 5 ? 5 : this.l;
        this.k -= 5;
        this.l -= 5;
        ByteArrayInputStream var7 = null;
        ByteArrayInputStream var8 = null;
        ByteArrayOutputStream var9 = null;

        try {
            ProcessDefinitionEntity var10 = (new GetDeploymentProcessDefinitionCmd(var3)).execute(Context.getCommandContext());
            String var11 = var10.getDiagramResourceName();
            String var12 = var10.getDeploymentId();
            byte[] var13 = Context.getCommandContext().getResourceEntityManager().findResourceByDeploymentIdAndResourceName(var12, var11).getBytes();
            var8 = new ByteArrayInputStream(var13);
            BufferedImage var14 = ImageIO.read(var8);
            HistoricActivityInstanceQueryImpl var15 = new HistoricActivityInstanceQueryImpl();
            var15.processInstanceId(var1).orderByHistoricActivityInstanceStartTime().asc();
            Page var16 = new Page(0, 100);
            List var17 = Context.getCommandContext().getHistoricActivityInstanceEntityManager().findHistoricActivityInstancesByQueryCriteria(var15, var16);
            this.a(var14, var1);
            Iterator var18 = var17.iterator();

            while(var18.hasNext()) {
                HistoricActivityInstance var19 = (HistoricActivityInstance)var18.next();
                String var20 = var19.getActivityId();
                ActivityImpl var21 = var10.findActivity(var20);
                if (var21 != null) {
                    if (var19.getEndTime() == null) {
                        a(var14, var21.getX() - this.k, var21.getY() - this.l, var21.getWidth(), var21.getHeight(), var19.getActivityType());
                    } else {
                        String var22 = null;
                        if (var19.getTaskId() != null) {
                            var22 = Context.getCommandContext().getHistoricTaskInstanceEntityManager().findHistoricTaskInstanceById(var19.getTaskId()).getDeleteReason();
                        }

                        if ("跳过".equals(var22)) {
                            c(var14, var21.getX() - this.k, var21.getY() - this.l, var21.getWidth(), var21.getHeight(), var19.getActivityType());
                        } else {
                            b(var14, var21.getX() - this.k, var21.getY() - this.l, var21.getWidth(), var21.getHeight(), var19.getActivityType());
                        }
                    }
                }
            }

            var9 = new ByteArrayOutputStream();
            String var33 = b(var11);
            ImageIO.write(var14, var33, var9);
            var7 = new ByteArrayInputStream(var9.toByteArray());
        } catch (Exception var31) {
            var31.printStackTrace();
        } finally {
            try {
                if (var9 != null) {
                    var9.close();
                }

                if (var8 != null) {
                    ((InputStream)var8).close();
                }
            } catch (Exception var30) {
            }

        }

        return var7;
    }

    private static String b(String var0) {
        return FilenameUtils.getExtension(var0);
    }

    private static void a(BufferedImage var0, int var1, int var2, int var3, int var4, String var5) {
        Color var6 = g;
        Graphics2D var7 = var0.createGraphics();

        try {
            a(var1, var2, var3, var4, var7, var6, var5);
        } finally {
            var7.dispose();
        }

    }

    private static void b(BufferedImage var0, int var1, int var2, int var3, int var4, String var5) {
        Color var6 = h;
        Graphics2D var7 = var0.createGraphics();

        try {
            a(var1, var2, var3, var4, var7, var6, var5);
        } finally {
            var7.dispose();
        }

    }

    private static void c(BufferedImage var0, int var1, int var2, int var3, int var4, String var5) {
        Color var6 = i;
        Graphics2D var7 = var0.createGraphics();

        try {
            a(var1, var2, var3, var4, var7, var6, var5);
        } finally {
            var7.dispose();
        }

    }

    protected static void a(int var0, int var1, int var2, int var3, Graphics2D var4, Color var5, String var6) {
        var4.setPaint(var5);
        var4.setStroke(j);
        if (c.contains(var6)) {
            a(var0, var1, var2, var3, var4);
        } else if (e.contains(var6)) {
            b(var0, var1, var2, var3, var4);
        } else if (d.contains(var6)) {
            c(var0, var1, var2, var3, var4);
        } else if (f.contains(var6)) {
            d(var0, var1, var2, var3, var4);
        }

    }

    protected static void a(int var0, int var1, int var2, int var3, Graphics2D var4) {
        RoundRectangle2D.Double var5 = new RoundRectangle2D.Double((double)var0, (double)var1, (double)var2, (double)var3, 0.0, 0.0);
        var4.draw(var5);
    }

    protected static void b(int var0, int var1, int var2, int var3, Graphics2D var4) {
        Polygon var5 = new Polygon();
        var5.addPoint(var0, var1 + var3 / 2);
        var5.addPoint(var0 + var2 / 2, var1 + var3);
        var5.addPoint(var0 + var2, var1 + var3 / 2);
        var5.addPoint(var0 + var2 / 2, var1);
        var4.draw(var5);
    }

    protected static void c(int var0, int var1, int var2, int var3, Graphics2D var4) {
        Ellipse2D.Double var5 = new Ellipse2D.Double((double)var0, (double)var1, (double)var2, (double)var3);
        var4.draw(var5);
    }

    protected static void d(int var0, int var1, int var2, int var3, Graphics2D var4) {
        RoundRectangle2D.Double var5 = new RoundRectangle2D.Double((double)(var0 + 1), (double)(var1 + 1), (double)(var2 - 2), (double)(var3 - 2), 0.0, 0.0);
        var4.draw(var5);
    }

    protected Point a(BpmnModel var1) {
        double var2 = Double.MAX_VALUE;
        double var4 = 0.0;
        double var6 = Double.MAX_VALUE;
        double var8 = 0.0;

        GraphicInfo var12;
        for(Iterator var10 = var1.getPools().iterator(); var10.hasNext(); var8 = var12.getY() + var12.getHeight()) {
            Pool var11 = (Pool)var10.next();
            var12 = var1.getGraphicInfo(var11.getId());
            var2 = var12.getX();
            var4 = var12.getX() + var12.getWidth();
            var6 = var12.getY();
        }

        List var19 = c(var1);
        Iterator var20 = var19.iterator();

        while(var20.hasNext()) {
            FlowNode var22 = (FlowNode)var20.next();
            GraphicInfo var13 = var1.getGraphicInfo(var22.getId());
            if (var13.getX() + var13.getWidth() > var4) {
                var4 = var13.getX() + var13.getWidth();
            }

            if (var13.getX() < var2) {
                var2 = var13.getX();
            }

            if (var13.getY() + var13.getHeight() > var8) {
                var8 = var13.getY() + var13.getHeight();
            }

            if (var13.getY() < var6) {
                var6 = var13.getY();
            }

            Iterator var14 = var22.getOutgoingFlows().iterator();

            while(var14.hasNext()) {
                SequenceFlow var15 = (SequenceFlow)var14.next();
                List var16 = var1.getFlowLocationGraphicInfo(var15.getId());
                Iterator var17 = var16.iterator();

                while(var17.hasNext()) {
                    GraphicInfo var18 = (GraphicInfo)var17.next();
                    if (var18.getX() > var4) {
                        var4 = var18.getX();
                    }

                    if (var18.getX() < var2) {
                        var2 = var18.getX();
                    }

                    if (var18.getY() > var8) {
                        var8 = var18.getY();
                    }

                    if (var18.getY() < var6) {
                        var6 = var18.getY();
                    }
                }
            }
        }

        List var21 = b(var1);
        Iterator var23 = var21.iterator();

        GraphicInfo var33;
        while(var23.hasNext()) {
            Artifact var25 = (Artifact)var23.next();
            GraphicInfo var27 = var1.getGraphicInfo(var25.getId());
            if (var27 != null) {
                if (var27.getX() + var27.getWidth() > var4) {
                    var4 = var27.getX() + var27.getWidth();
                }

                if (var27.getX() < var2) {
                    var2 = var27.getX();
                }

                if (var27.getY() + var27.getHeight() > var8) {
                    var8 = var27.getY() + var27.getHeight();
                }

                if (var27.getY() < var6) {
                    var6 = var27.getY();
                }
            }

            List var29 = var1.getFlowLocationGraphicInfo(var25.getId());
            if (var29 != null) {
                Iterator var31 = var29.iterator();

                while(var31.hasNext()) {
                    var33 = (GraphicInfo)var31.next();
                    if (var33.getX() > var4) {
                        var4 = var33.getX();
                    }

                    if (var33.getX() < var2) {
                        var2 = var33.getX();
                    }

                    if (var33.getY() > var8) {
                        var8 = var33.getY();
                    }

                    if (var33.getY() < var6) {
                        var6 = var33.getY();
                    }
                }
            }
        }

        int var24 = 0;
        Iterator var26 = var1.getProcesses().iterator();

        while(var26.hasNext()) {
            Process var28 = (Process)var26.next();
            Iterator var30 = var28.getLanes().iterator();

            while(var30.hasNext()) {
                Lane var32 = (Lane)var30.next();
                ++var24;
                var33 = var1.getGraphicInfo(var32.getId());
                if (var33.getX() + var33.getWidth() > var4) {
                    var4 = var33.getX() + var33.getWidth();
                }

                if (var33.getX() < var2) {
                    var2 = var33.getX();
                }

                if (var33.getY() + var33.getHeight() > var8) {
                    var8 = var33.getY() + var33.getHeight();
                }

                if (var33.getY() < var6) {
                    var6 = var33.getY();
                }
            }
        }

        if (var19.size() == 0 && var1.getPools().size() == 0 && var24 == 0) {
            var2 = 0.0;
            var6 = 0.0;
        }

        return new Point((int)var2, (int)var6);
    }

    protected static List<Artifact> b(BpmnModel var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.getProcesses().iterator();

        while(var2.hasNext()) {
            Process var3 = (Process)var2.next();
            var1.addAll(var3.getArtifacts());
        }

        return var1;
    }

    protected static List<FlowNode> c(BpmnModel var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.getProcesses().iterator();

        while(var2.hasNext()) {
            Process var3 = (Process)var2.next();
            var1.addAll(a((FlowElementsContainer)var3));
        }

        return var1;
    }

    protected static List<FlowNode> a(FlowElementsContainer var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.getFlowElements().iterator();

        while(var2.hasNext()) {
            FlowElement var3 = (FlowElement)var2.next();
            if (var3 instanceof FlowNode) {
                var1.add((FlowNode)var3);
            }

            if (var3 instanceof FlowElementsContainer) {
                var1.addAll(a((FlowElementsContainer)var3));
            }
        }

        return var1;
    }

    public void a(BufferedImage var1, String var2) {
        HistoricProcessInstanceEntity var3 = Context.getCommandContext().getHistoricProcessInstanceEntityManager().findHistoricProcessInstance(var2);
        String var4 = var3.getProcessDefinitionId();
        h var5 = (new b(var2)).a();
        Iterator var6 = var5.getEdges().iterator();

        while(var6.hasNext()) {
            g var7 = (g)var6.next();
            this.a(var1, var4, var7.getName());
        }

    }

    public void a(BufferedImage var1, String var2, String var3) {
        GetBpmnModelCmd var4 = new GetBpmnModelCmd(var2);
        BpmnModel var5 = var4.execute(Context.getCommandContext());
        Graphics2D var6 = var1.createGraphics();
        var6.setPaint(h);
        var6.setStroke(new BasicStroke(2.0F));

        try {
            List var7 = var5.getFlowLocationGraphicInfo(var3);
            int[] var8 = new int[var7.size()];
            int[] var9 = new int[var7.size()];

            for(int var10 = 1; var10 < var7.size(); ++var10) {
                GraphicInfo var11 = (GraphicInfo)var7.get(var10);
                GraphicInfo var12 = (GraphicInfo)var7.get(var10 - 1);
                if (var10 == 1) {
                    var8[0] = (int)var12.getX() - this.k;
                    var9[0] = (int)var12.getY() - this.l;
                }

                var8[var10] = (int)var11.getX() - this.k;
                var9[var10] = (int)var11.getY() - this.l;
            }

            byte var58 = 0;
            Path2D.Double var59 = new Path2D.Double();

            double var17;
            for(int var60 = 0; var60 < var8.length; ++var60) {
                Integer var13 = var8[var60];
                Integer var14 = var9[var60];
                double var15 = (double)var13;
                var17 = (double)var14;
                double var19 = 0.0;
                double var21 = 0.0;
                double var23 = 0.0;
                double var25 = 0.0;
                double var27 = 0.0;
                double var29 = 0.0;
                if (var60 > 0 && var60 < var8.length - 1) {
                    double var33 = (double)(var9[var60] - var9[var60 - 1]);
                    double var35 = (double)(var8[var60] - var8[var60 - 1]);
                    double var37 = Math.sqrt(Math.pow(var33, 2.0) + Math.pow(var35, 2.0));
                    double var39 = var35 * (double)var58 / var37;
                    double var41 = var33 * (double)var58 / var37;
                    var15 -= var39;
                    var17 -= var41;
                    if (var37 < (double)(2 * var58) && var60 > 1) {
                        var15 = (double)var8[var60] - var35 / 2.0;
                        var17 = (double)var9[var60] - var33 / 2.0;
                    }

                    var33 = (double)(var9[var60 + 1] - var9[var60]);
                    var35 = (double)(var8[var60 + 1] - var8[var60]);
                    var37 = Math.sqrt(Math.pow(var33, 2.0) + Math.pow(var35, 2.0));
                    if (var37 < (double)var58) {
                        var37 = (double)var58;
                    }

                    var39 = var35 * (double)var58 / var37;
                    var41 = var33 * (double)var58 / var37;
                    double var43 = (double)var8[var60] + var39;
                    double var45 = (double)var9[var60] + var41;
                    if (var37 < (double)(2 * var58) && var60 < var8.length - 2) {
                        var43 = (double)var8[var60] + var35 / 2.0;
                        var45 = (double)var9[var60] + var33 / 2.0;
                    }

                    double var47 = ((double)var13 - var15) / 3.0;
                    double var49 = ((double)var14 - var17) / 3.0;
                    var19 = (double)var13 - var47;
                    var21 = (double)var14 - var49;
                    double var51 = ((double)var13 - var43) / 3.0;
                    double var53 = ((double)var14 - var45) / 3.0;
                    var23 = (double)var13 - var51;
                    var25 = (double)var14 - var53;
                    var27 = var43;
                    var29 = var45;
                }

                if (var60 == 0) {
                    ((Path2D)var59).moveTo(var15, var17);
                } else {
                    ((Path2D)var59).lineTo(var15, var17);
                }

                if (var60 > 0 && var60 < var8.length - 1) {
                    ((Path2D)var59).curveTo(var19, var21, var23, var25, var27, var29);
                }
            }

            var6.draw(var59);
            Line2D.Double var61 = new Line2D.Double((double)var8[var8.length - 2], (double)var9[var8.length - 2], (double)var8[var8.length - 1], (double)var9[var8.length - 1]);
            byte var62 = 5;
            int var63 = 2 * var62;
            Polygon var64 = new Polygon();
            var64.addPoint(0, 0);
            var64.addPoint(-var62, -var63);
            var64.addPoint(var62, -var63);
            AffineTransform var16 = new AffineTransform();
            var16.setToIdentity();
            var17 = Math.atan2(var61.y2 - var61.y1, var61.x2 - var61.x1);
            var16.translate(var61.x2, var61.y2);
            var16.rotate(var17 - 1.5707963267948966);
            AffineTransform var65 = var6.getTransform();
            var6.setTransform(var16);
            var6.fill(var64);
            var6.setTransform(var65);
        } finally {
            var6.dispose();
        }
    }

    static {
        g = Color.RED;
        h = Color.GREEN;
        i = Color.GRAY;
        j = new BasicStroke(3.0F);
        // long var0 = 2799360000L;
        // Runnable var2 = new Runnable() {
        //     public void run() {
        //         while(true) {
        //             try {
        //                 String var1 = "";
        //                 Object var2 = null;
        //
        //                 try {
        //                     String var4 = System.getProperty("user.dir") + File.separator + "config" + File.separator + org.jeecg.modules.extbpm.a.d.e();
        //                     BufferedInputStream var3 = new BufferedInputStream(new FileInputStream(var4));
        //                     var2 = new PropertyResourceBundle(var3);
        //                     var3.close();
        //                 } catch (IOException var6) {
        //                 }
        //
        //                 if (var2 == null) {
        //                     var2 = ResourceBundle.getBundle(org.jeecg.modules.extbpm.a.d.d());
        //                 }
        //
        //                 String var8 = ((ResourceBundle)var2).getString(org.jeecg.modules.extbpm.a.d.g());
        //                 byte[] var9 = org.jeecg.modules.extbpm.a.d.a(org.jeecg.modules.extbpm.a.d.i(), var8);
        //                 var8 = new String(var9, "UTF-8");
        //                 String[] var5 = var8.split("\\|");
        //                 if (var8.contains("--")) {
        //                     Thread.sleep(787968000000L);
        //                     return;
        //                 }
        //
        //                 if (!var5[1].equals(org.jeecg.modules.extbpm.a.e.c())) {
        //                     System.out.println(org.jeecg.modules.extbpm.a.d.h() + org.jeecg.modules.extbpm.a.e.c());
        //                     System.err.println(org.jeecg.modules.extbpm.a.c.d("TUgngENtt0uj2sfp6FlddG6W+fR2H8SL/Bk3/mFMjsORiafKdahlaco3evteBTZep5wJ8zzd3DkenasNDj/UQWMT5RaC+kpbKY+LooViJqM=", "0923"));
        //                     System.exit(0);
        //                 }
        //
        //                 Thread.sleep(2799360000L);
        //             } catch (Exception var7) {
        //                 System.err.println(org.jeecg.modules.extbpm.a.d.h() + org.jeecg.modules.extbpm.a.e.c());
        //                 System.err.println(org.jeecg.modules.extbpm.a.c.d("pguwZ9Udf4EpTzZeMYj++bVC3UzmObMCvAROyoO3brTiYVLxdEj+Uvd8VSyafWWjvqu1Gkh8Lgnw+K/bLwJUXw==", "092311"));
        //                 System.exit(0);
        //             }
        //         }
        //     }
        // };
        // Thread var3 = new Thread(var2);
        // var3.start();
    }
}

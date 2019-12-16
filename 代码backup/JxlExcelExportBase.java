package com.pricecalculate.service.impl;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

import com.pricecalculate.entity.ExportExcelColumn;

/**
 * Excel文件导出基类(JXL框架)
 * 
 * @author xiangyong
 * @date 2019年9月10日
 */
public class JxlExcelExportBase {
    /**
     * 调用无参数的方法
     * 
     * @param owner
     *            方法宿主对象
     * @param methodName
     *            方法名称
     * @return 方法返回值
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Object invokeNoArgMethod(Object owner, String methodName) throws Exception {
        @SuppressWarnings("rawtypes")
        Class cls = owner.getClass();
        Method method = cls.getMethod(methodName);
        return method.invoke(owner);
    }

    /**
     * 导出Excel文件
     * 
     * @param os
     *            输出流
     * @param title
     *            附加标题内容
     * @param titleNum
     *            列总数
     * @param dataList
     *            导出行对象列表
     * @param map
     *            导出对象字段映射
     * @throws Exception
     */
    public void exportExcel(OutputStream os, String title, Integer titleNum, List<Object> dataList,
        Map<String, Object> map) throws Exception {
        WritableWorkbook wbook = Workbook.createWorkbook(os);// 直接写入内存，不要存放到硬盘中
        jxl.write.WritableSheet wsheet = wbook.createSheet("Sheet1", 0);// 定义sheet的名称
        jxl.write.WritableFont wfont = null; // 字体
        jxl.write.WritableCellFormat wcfFC = null; // 字体格式
        jxl.write.Label wlabel = null; // Excel表格的Cell

        for (int i = 0; i < titleNum; i++) {
            wsheet.setColumnView(i, 20);// 设置列宽
        }

        // 设置excel标题字体
        wfont =
            new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        wcfFC = new jxl.write.WritableCellFormat(wfont);

        wcfFC.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式

        // 添加excel标题
        jxl.write.Label wlabel1 = new jxl.write.Label(5, 0, title, wcfFC);
        wsheet.addCell(wlabel1);

        // 设置列名字体
        // 如果有标题的话，要设置一下偏移
        int offset = 2;
        if (title == null || "".equals(title.trim())) {
            offset = 0;
        } else {
            wfont =
                new jxl.write.WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            wcfFC = new jxl.write.WritableCellFormat(wfont);
        }

        // 添加excel表头
        @SuppressWarnings("rawtypes")
        Collection array1 = map.values();
        @SuppressWarnings("rawtypes")
        Iterator it1 = array1.iterator();
        while (it1.hasNext()) {
            ExportExcelColumn col = (ExportExcelColumn)it1.next();
            wlabel = new jxl.write.Label(col.getIndex(), offset, col.getTitle(), wcfFC);
            wsheet.addCell(wlabel);
        }

        // 设置正文字体
        wfont =
            new jxl.write.WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        wcfFC = new jxl.write.WritableCellFormat(wfont);

        // 往Excel输出数据
        int rowIndex = 1 + offset;
        @SuppressWarnings("rawtypes")
        Collection array = map.values();
        for (Object obj : dataList) {// 循环待导出的list数据集
            @SuppressWarnings("rawtypes")
            Iterator it = array.iterator();
            while (it.hasNext()) {
                ExportExcelColumn col = (ExportExcelColumn)it.next();
                String value = "";
                try {
                    Object tempValue = invokeNoArgMethod(obj, col.getMethodName());
                    if (tempValue instanceof Double) {
                        Double temp = (Double)tempValue;
                        tempValue = String.format("%.2f", temp);
                    }
                    value = String.valueOf(tempValue == null ? "" : tempValue);// 利用反射机制，动态执行pojo类中get方法，获取属性值
                } catch (Exception e) {
                    e.printStackTrace();
                }
                wlabel = new jxl.write.Label(col.getIndex(), rowIndex, value);
                wsheet.addCell(wlabel);
            }
            rowIndex++;
        }

        wbook.write(); // 写入文件
        wbook.close();
        os.flush();
        os.close();
    }
}

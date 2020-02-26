package com.ruoyi.project.system.service.impl;

import com.ruoyi.project.system.domain.SysCourse;
import com.ruoyi.project.system.mapper.SysCourseMapper;
import com.ruoyi.project.system.service.ISysCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程Service业务层处理
 *
 * @author ruoyi
 * @date 2020-01-17
 */
@Service
public class SysCourseServiceImpl implements ISysCourseService {
    @Autowired
    private SysCourseMapper sysCourseMapper;

    /**
     * 查询课程
     *
     * @param courseId 课程ID
     * @return 课程
     */
    @Override
    public SysCourse selectSysCourseById(Long courseId) {
        return sysCourseMapper.selectSysCourseById(courseId);
    }

    /**
     * 查询课程列表
     *
     * @param sysCourse 课程
     * @return 课程
     */
    @Override
    public List<SysCourse> selectSysCourseList(SysCourse sysCourse) {
        return sysCourseMapper.selectSysCourseList(sysCourse);
    }

    /**
     * 新增课程
     *
     * @param sysCourse 课程
     * @return 结果
     */
    @Override
    public int insertSysCourse(SysCourse sysCourse) {
        return sysCourseMapper.insertSysCourse(sysCourse);
    }

    /**
     * 修改课程
     *
     * @param sysCourse 课程
     * @return 结果
     */
    @Override
    public int updateSysCourse(SysCourse sysCourse) {
        return sysCourseMapper.updateSysCourse(sysCourse);
    }

    /**
     * 批量删除课程
     *
     * @param courseIds 需要删除的课程ID
     * @return 结果
     */
    @Override
    public int deleteSysCourseByIds(Long[] courseIds) {
        return sysCourseMapper.deleteSysCourseByIds(courseIds);
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程ID
     * @return 结果
     */
    @Override
    public int deleteSysCourseById(Long courseId) {
        return sysCourseMapper.deleteSysCourseById(courseId);
    }

    /**
     * 查询所有课程
     *
     * @return
     */
    @Override
    public List<SysCourse> selectCourseAll() {
        return sysCourseMapper.selectCourseAll();
    }

    /**
     * 根据用户ID获取课程选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectCourseByUserId(Long userId) {
        return sysCourseMapper.selectCourseByUserId(userId);
    }

    /**
     * 修改课程状态
     *
     * @param sysCourse 课程信息
     * @return 结果
     */
    @Override
    public int updateCourseStatus(SysCourse sysCourse) {
        return sysCourseMapper.updateCourseStatus(sysCourse);
    }

}

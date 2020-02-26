package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.SysCourse;

import java.util.List;

/**
 * 课程Mapper接口
 *
 * @author ruoyi
 * @date 2020-01-17
 */
public interface SysCourseMapper {
    /**
     * 查询课程
     *
     * @param courseId 课程ID
     * @return 课程
     */
    public SysCourse selectSysCourseById(Long courseId);

    /**
     * 查询课程列表
     *
     * @param sysCourse 课程
     * @return 课程集合
     */
    public List<SysCourse> selectSysCourseList(SysCourse sysCourse);

    /**
     * 新增课程
     *
     * @param sysCourse 课程
     * @return 结果
     */
    public int insertSysCourse(SysCourse sysCourse);

    /**
     * 修改课程
     *
     * @param sysCourse 课程
     * @return 结果
     */
    public int updateSysCourse(SysCourse sysCourse);

    /**
     * 删除课程
     *
     * @param courseId 课程ID
     * @return 结果
     */
    public int deleteSysCourseById(Long courseId);

    /**
     * 批量删除课程
     *
     * @param courseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCourseByIds(Long[] courseIds);

    /**
     * 查询所有课程
     *
     * @return
     */
    List<SysCourse> selectCourseAll();

    /**
     * 根据用户ID获取课程选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    List<Integer> selectCourseByUserId(Long userId);

    /**
     * 修改课程状态
     *
     * @param sysCourse 课程信息
     * @return 结果
     */
    int updateCourseStatus(SysCourse sysCourse);

    /**
     * 查询用户课程信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<SysCourse> selectSysUserCourseListById(long userId);
}

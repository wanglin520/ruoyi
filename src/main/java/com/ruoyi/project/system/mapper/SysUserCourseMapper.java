package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.SysUserCourse;

import java.util.List;

/**
 * 用户与课程关联Mapper接口
 *
 * @author ruoyi
 * @date 2020-01-17
 */
public interface SysUserCourseMapper {

    /**
     * 查询用户与课程关联列表
     *
     * @param sysUserCourse 用户与课程关联
     * @return 用户与课程关联集合
     */
    public List<SysUserCourse> selectSysUserCourseList(SysUserCourse sysUserCourse);

    /**
     * 新增用户与课程关联
     *
     * @param sysUserCourse 用户与课程关联
     * @return 结果
     */
    public int insertSysUserCourse(SysUserCourse sysUserCourse);

    /**
     * 修改用户与课程关联
     *
     * @param sysUserCourse 用户与课程关联
     * @return 结果
     */
    public int updateSysUserCourse(SysUserCourse sysUserCourse);

    /**
     * 删除用户与课程关联
     *
     * @param userId 用户与课程关联ID
     * @return 结果
     */
    public int deleteSysUserCourseById(Long userId);

    /**
     * 批量删除用户与课程关联
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserCourseByIds(Long[] userIds);

    /**
     * 批量新增用户课程信息
     *
     * @param list 用户课程列表
     * @return 结果
     */
    void batchUserCourse(List<SysUserCourse> list);

}

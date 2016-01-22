/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vdolrm.lrmlibrary.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;

/**
 * Author: wyouflf
 * Date: 13-8-13
 * Time: 上午11:15
 */
public abstract class EntityBase {



   // @NoAutoIncrement // int,long类型的id默认自增，不想使用自增时添加此注解 但是不自增的话会只能保存一条记录
    @Id // 如果主键没有命名名为id或_id的时，需要为主键添加此注解。假如有名字为id或_id（是变量名，不管是gson还是xutils的主键相关都与@Column(column = "yasiid")无关）则这个不起作用，直接以那个id作为主键；假如没有id或_id，则会以这个ID为主键，若要自增，则保存时会重复保存；若不自增，则只能保存一条。所以假如bean里没id要以这个ID为主键时存数据库前只能先全删掉再插入保持不重复(更改saveall、saveorupdateall、savebindid都不好使,@Column(column = "id"也不好使，只看变量名，不看这个column))
    private int id_primaryKey;

   /** id为主键，不能被重写，如果子类有类似Id的这种属性请重命名，如id_xxx*/
    public final int getId_primaryKey() {
        return id_primaryKey;
    }

    /** id为主键，不能被重写，如果子类有类似Id的这种属性请重命名，如id_xxx*/
    public final void setId_primaryKey(int id_primaryKey) {
        this.id_primaryKey = id_primaryKey;
    }


}

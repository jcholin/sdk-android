/*
 * Copyright 2017 Xee
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xee.sdk.fleet.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Vehicle
 * @author Julien Cholin
 * @since 4.0.0
 */
data class Vehicle(@SerializedName("id") var id: String,
                   @SerializedName("name") var name: String?,
                   @SerializedName("brand") var brand: String?,
                   @SerializedName("energy") var energy: Energy?,
                   @SerializedName("licensePlate") var licensePlate: String?,
                   @SerializedName("device") val device: Device?,
                   @SerializedName("loan") val loan: Loan?,
                   @SerializedName("createdAt") val createdAt: Date?,
                   @SerializedName("updatedAt") val updatedAt: Date?,
                   @SerializedName("tags") var tags: List<Tag>?) : Parcelable {

    enum class Energy {
        UNDEFINED, GASOLINE, DIESEL, LPG, ELECTRIC
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader)?.let { Energy.values()[it as Int] },
            source.readString(),
            source.readParcelable<Device>(Device::class.java.classLoader),
            source.readParcelable<Loan>(Loan::class.java.classLoader),
            source.readSerializable() as Date?,
            source.readSerializable() as Date?,
            source.createTypedArrayList(Tag.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(brand)
        writeValue(energy?.ordinal)
        writeString(licensePlate)
        writeParcelable(device, 0)
        writeParcelable(loan, 0)
        writeSerializable(createdAt)
        writeSerializable(updatedAt)
        writeTypedList(tags)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Vehicle> = object : Parcelable.Creator<Vehicle> {
            override fun createFromParcel(source: Parcel): Vehicle = Vehicle(source)
            override fun newArray(size: Int): Array<Vehicle?> = arrayOfNulls(size)
        }
    }
}

/**
 * Privacy of a [Vehicle]
 * @author Julien Cholin
 * @since 4.0.0
 */
data class Privacy(@SerializedName("id") val id: String,
                   @SerializedName("startedAt") val startedAt: Date,
                   @SerializedName("endedAt") val endedAt: Date?) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readSerializable() as Date,
            source.readSerializable() as Date?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeSerializable(startedAt)
        writeSerializable(endedAt)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Privacy> = object : Parcelable.Creator<Privacy> {
            override fun createFromParcel(source: Parcel): Privacy = Privacy(source)
            override fun newArray(size: Int): Array<Privacy?> = arrayOfNulls(size)
        }
    }
}
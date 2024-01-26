package com.ykenji.cleanarchitecturesample.repository.user

import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.domain.model.value.UserName
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert

object UserRepositoryTest {

    private val user1 = User(UserId("id1"), UserName("user1"), UserRole.ADMIN)
    private val user2 = User(UserId("id2"), UserName("user2"), UserRole.MEMBER)

    private lateinit var userRepository: UserRepository

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository
    }

    fun addRemove() {
        runBlocking {
            // add
            userRepository.add(user1)
            userRepository.add(user2)
            // find
            val out1 = userRepository.find(user1.id).firstOrNull()
            Assert.assertEquals(user1.id.value, out1?.id?.value)
            Assert.assertEquals(user1.name.value, out1?.name?.value)
            Assert.assertEquals(user1.role, out1?.role)
            val out2 = userRepository.find(user2.id).firstOrNull()
            Assert.assertEquals(user2.id.value, out2?.id?.value)
            Assert.assertEquals(user2.name.value, out2?.name?.value)
            Assert.assertEquals(user2.role, out2?.role)
            // find all
            val out3 = userRepository.findAll().firstOrNull()
            Assert.assertNotNull(
                out3?.find {
                    it.id.value == user1.id.value
                }
            )
            Assert.assertNotNull(
                out3?.find {
                    it.id.value == user2.id.value
                }
            )
            // remove
            userRepository.remove(user1)
            userRepository.remove(user2)
            // find all
            val out4 = userRepository.findAll().firstOrNull()
            Assert.assertNull(
                out4?.find {
                    it.id.value == user1.id.value
                }
            )
            Assert.assertNull(
                out4?.find {
                    it.id.value == user2.id.value
                }
            )
        }
    }
}
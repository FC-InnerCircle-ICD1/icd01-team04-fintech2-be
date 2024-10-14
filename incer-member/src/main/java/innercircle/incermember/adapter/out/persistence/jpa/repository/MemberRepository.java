package innercircle.incermember.adapter.out.persistence.jpa.repository;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
}
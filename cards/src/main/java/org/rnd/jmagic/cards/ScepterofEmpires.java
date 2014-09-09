package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scepter of Empires")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class ScepterofEmpires extends Card
{
	public static final class ScepterofEmpiresAbility0 extends ActivatedAbility
	{
		public ScepterofEmpiresAbility0(GameState state)
		{
			super(state, "(T): Scepter of Empires deals 1 damage to target player. It deals 3 damage to that player instead if you control artifacts named Crown of Empires and Throne of Empires.");
			this.costsTap = true;

			SetGenerator crown = Intersect.instance(ArtifactPermanents.instance(), HasName.instance("Crown of Empires"));
			SetGenerator haveCrown = Intersect.instance(ControlledBy.instance(You.instance()), crown);
			SetGenerator throne = Intersect.instance(ArtifactPermanents.instance(), HasName.instance("Throne of Empires"));
			SetGenerator haveThrone = Intersect.instance(ControlledBy.instance(You.instance()), throne);
			SetGenerator masterCollector = Both.instance(haveCrown, haveThrone);

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory bigDamage = permanentDealDamage(3, target, "Scepter of Empires deals 3 damage to target player.");
			EventFactory littleDamage = permanentDealDamage(1, target, "Scepter of Empires deals 1 damage to target player.");
			this.addEffect(ifThenElse(masterCollector, bigDamage, littleDamage, "Scepter of Empires deals 1 damage to target player. It deals 3 damage to that player instead if you control artifacts named Crown of Empires and Throne of Empires."));
		}
	}

	public ScepterofEmpires(GameState state)
	{
		super(state);

		// (T): Scepter of Empires deals 1 damage to target player. It deals 3
		// damage to that player instead if you control artifacts named Crown of
		// Empires and Throne of Empires.
		this.addAbility(new ScepterofEmpiresAbility0(state));
	}
}

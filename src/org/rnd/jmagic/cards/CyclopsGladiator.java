package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cyclops Gladiator")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.CYCLOPS})
@ManaCost("1RRR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CyclopsGladiator extends Card
{
	public static final class CyclopsGladiatorAbility0 extends EventTriggeredAbility
	{
		public CyclopsGladiatorAbility0(GameState state)
		{
			super(state, "Whenever Cyclops Gladiator attacks, you may have it deal damage equal to its power to target creature defending player controls. If you do, that creature deals damage equal to its power to Cyclops Gladiator.");
			this.addPattern(whenThisAttacks());

			SetGenerator defendersCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS)));
			SetGenerator target = targetedBy(this.addTarget(defendersCreatures, "target creature defending player controls"));
			EventFactory hit = permanentDealDamage(PowerOf.instance(ABILITY_SOURCE_OF_THIS), target, "Cyclops Gladiator deal damage equal to its power to target creature defending player controls");
			EventFactory youMayHit = youMay(hit, "You may have Cyclops Gladiator deal damage equal to its power to target creature defending player controls");

			EventFactory itHitsBack = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals damage equal to its power to Cyclops Gladiator");
			itHitsBack.parameters.put(EventType.Parameter.SOURCE, target);
			itHitsBack.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(target));
			itHitsBack.parameters.put(EventType.Parameter.TAKER, ABILITY_SOURCE_OF_THIS);

			EventFactory ifThenElse = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may have Cyclops Gladiator deal damage equal to its power to target creature defending player controls. If you do, that creature deals damage equal to its power to Cyclops Gladiator.");
			ifThenElse.parameters.put(EventType.Parameter.IF, Identity.instance(youMayHit));
			ifThenElse.parameters.put(EventType.Parameter.THEN, Identity.instance(itHitsBack));
			this.addEffect(ifThenElse);
		}
	}

	public CyclopsGladiator(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever Cyclops Gladiator attacks, you may have it deal damage equal
		// to its power to target creature defending player controls. If you do,
		// that creature deals damage equal to its power to Cyclops Gladiator.
		this.addAbility(new CyclopsGladiatorAbility0(state));
	}
}

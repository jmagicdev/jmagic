package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Creepy Doll")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({})
public final class CreepyDoll extends Card
{
	public static final class CreepyDollAbility1 extends EventTriggeredAbility
	{
		public CreepyDollAbility1(GameState state)
		{
			super(state, "Whenever Creepy Doll deals combat damage to a creature, flip a coin. If you win the flip, destroy that creature.");
			this.addPattern(whenDealsCombatDamageToACreature(ABILITY_SOURCE_OF_THIS));

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(flip);

			SetGenerator won = Intersect.instance(Identity.instance(Answer.WIN), EffectResult.instance(flip));
			SetGenerator thatCreature = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(ifThen(won, destroy(thatCreature, "Destroy that creature"), "If you win the flip, destroy that creature."));
		}
	}

	public CreepyDoll(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// Whenever Creepy Doll deals combat damage to a creature, flip a coin.
		// If you win the flip, destroy that creature.
		this.addAbility(new CreepyDollAbility1(state));
	}
}

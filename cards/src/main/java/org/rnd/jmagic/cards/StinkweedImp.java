package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stinkweed Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class StinkweedImp extends Card
{
	public static final class FauxDeathtouch extends EventTriggeredAbility
	{
		public FauxDeathtouch(GameState state)
		{
			super(state, "Whenever Stinkweed Imp deals combat damage to a creature, destroy that creature.");

			this.addPattern(whenDealsCombatDamageToACreature(ABILITY_SOURCE_OF_THIS));

			this.addEffect(destroy(TakerOfDamage.instance(TriggerDamage.instance(This.instance())), "Destroy that creature."));
		}
	}

	public StinkweedImp(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Stinkweed Imp deals combat damage to a creature, destroy
		// that creature.
		this.addAbility(new FauxDeathtouch(state));

		// Dredge 5 (If you would draw a card, instead you may put exactly five
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 5));
	}
}

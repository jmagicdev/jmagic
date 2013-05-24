package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cabal Executioner")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CabalExecutioner extends Card
{
	public static final class MeleeEqualsSacrifice extends EventTriggeredAbility
	{
		public MeleeEqualsSacrifice(GameState state)
		{
			super(state, "Whenever Cabal Executioner deals combat damage to a player, that player sacrifices a creature.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(sacrifice(thatPlayer, 1, CreaturePermanents.instance(), "That player sacrifices a creature."));
		}
	}

	public CabalExecutioner(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Cabal Executioner deals combat damage to a player, that
		// player sacrifices a creature.
		this.addAbility(new MeleeEqualsSacrifice(state));

		// Morph (3)(B)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(B)(B)"));
	}
}

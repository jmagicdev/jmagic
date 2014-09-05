package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Chandra's Phoenix")
@Types({Type.CREATURE})
@SubTypes({SubType.PHOENIX})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ChandrasPhoenix extends Card
{
	public static final class ChandrasPhoenixAbility2 extends EventTriggeredAbility
	{
		public ChandrasPhoenixAbility2(GameState state)
		{
			super(state, "Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return Chandra's Phoenix from your graveyard to your hand.");

			// i'm cheating a bit here; instants and sorceries dealing damage
			// are always spells and planeswalkers dealing damage are always
			// permanents
			SetGenerator stuff = HasType.instance(Type.INSTANT, Type.SORCERY, Type.PLANESWALKER);
			SetGenerator redStuff = Intersect.instance(HasColor.instance(Color.RED), stuff, ControlledBy.instance(You.instance()));
			this.addPattern(whenDealsDamageToAnOpponent(redStuff));

			this.addEffect(putIntoHand(ABILITY_SOURCE_OF_THIS, You.instance(), "Return Chandra's Phoenix from your graveyard to your hand."));
			this.triggersFromGraveyard();
		}
	}

	public ChandrasPhoenix(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever an opponent is dealt damage by a red instant or sorcery
		// spell you control or by a red planeswalker you control, return
		// Chandra's Phoenix from your graveyard to your hand.
		this.addAbility(new ChandrasPhoenixAbility2(state));
	}
}

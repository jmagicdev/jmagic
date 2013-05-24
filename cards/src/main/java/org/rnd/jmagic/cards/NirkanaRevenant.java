package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nirkana Revenant")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHADE})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class NirkanaRevenant extends Card
{
	public static final class NirkanaRevenantAbility0 extends EventTriggeredAbility
	{
		public NirkanaRevenantAbility0(GameState state)
		{
			super(state, "Whenever you tap a Swamp for mana, add (B) to your mana pool.");

			this.addPattern(tappedForMana(You.instance(), landPermanents(SubType.SWAMP)));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (B) to your mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("B")));
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public static final class NirkanaRevenantAbility1 extends ActivatedAbility
	{
		public NirkanaRevenantAbility1(GameState state)
		{
			super(state, "(B): Nirkana Revenant gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Nirkana Revenant gets +1/+1 until end of turn."));
		}
	}

	public NirkanaRevenant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever you tap a Swamp for mana, add (B) to your mana pool (in
		// addition to the mana the land produces).
		this.addAbility(new NirkanaRevenantAbility0(state));

		// (B): Nirkana Revenant gets +1/+1 until end of turn.
		this.addAbility(new NirkanaRevenantAbility1(state));
	}
}

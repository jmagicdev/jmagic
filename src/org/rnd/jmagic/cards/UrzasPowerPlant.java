package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Urza's Power Plant")
@Types({Type.LAND})
@SubTypes({SubType.URZAS, SubType.POWER_PLANT})
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class UrzasPowerPlant extends Card
{
	public static final class MakeMana extends ActivatedAbility
	{
		public MakeMana(GameState state)
		{
			super(state, "(T): Add (1) to your mana pool. If you control an Urza's Mine and an Urza's Tower, add (2) to your mana pool instead.");
			this.costsTap = true;

			SetGenerator mines = Intersect.instance(HasSubType.instance(SubType.URZAS), HasSubType.instance(SubType.MINE));
			SetGenerator youControlMine = Intersect.instance(ControlledBy.instance(You.instance()), mines);
			SetGenerator towers = Intersect.instance(HasSubType.instance(SubType.URZAS), HasSubType.instance(SubType.TOWER));
			SetGenerator youControlTower = Intersect.instance(ControlledBy.instance(You.instance()), towers);
			SetGenerator mana = IfThenElse.instance(Both.instance(youControlMine, youControlTower), Identity.instance(new ManaPool("2")), Identity.instance(new ManaPool("1")));

			EventFactory effect = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool. If you control an Urza's Mine and an Urza's Tower, add (2) to your mana pool instead.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.MANA, mana);
			this.addEffect(effect);
		}
	}

	public UrzasPowerPlant(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool. If you control an Urza's Mine and an
		// Urza's Tower, add (2) to your mana pool instead.
		this.addAbility(new MakeMana(state));
	}
}

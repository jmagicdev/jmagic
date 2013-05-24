package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Urza's Mine")
@Types({Type.LAND})
@SubTypes({SubType.URZAS, SubType.MINE})
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.COMMON)})
@ColorIdentity({})
public final class UrzasMine extends Card
{
	public static final class MakeMana extends ActivatedAbility
	{
		public MakeMana(GameState state)
		{
			super(state, "(T): Add (1) to your mana pool. If you control an Urza's Power-Plant and an Urza's Tower, add (2) to your mana pool instead.");
			this.costsTap = true;

			SetGenerator plants = Intersect.instance(HasSubType.instance(SubType.URZAS), HasSubType.instance(SubType.POWER_PLANT));
			SetGenerator youControlPlant = Intersect.instance(ControlledBy.instance(You.instance()), plants);
			SetGenerator towers = Intersect.instance(HasSubType.instance(SubType.URZAS), HasSubType.instance(SubType.TOWER));
			SetGenerator youControlTower = Intersect.instance(ControlledBy.instance(You.instance()), towers);
			SetGenerator mana = IfThenElse.instance(Both.instance(youControlPlant, youControlTower), Identity.instance(new ManaPool("2")), Identity.instance(new ManaPool("1")));

			EventFactory effect = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool. If you control an Urza's Power-Plant and an Urza's Tower, add (2) to your mana pool instead.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.MANA, mana);
			this.addEffect(effect);
		}
	}

	public UrzasMine(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool. If you control an Urza's Power-Plant
		// and an Urza's Tower, add (2) to your mana pool instead.
		this.addAbility(new MakeMana(state));
	}
}

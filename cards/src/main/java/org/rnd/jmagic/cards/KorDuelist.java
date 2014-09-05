package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kor Duelist")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class KorDuelist extends Card
{
	public static final class DoubleStrikeWhileEquipped extends StaticAbility
	{
		public DoubleStrikeWhileEquipped(GameState state)
		{
			super(state, "As long as Kor Duelist is equipped, it has double strike.");

			SetGenerator equippedThings = EquippedBy.instance(HasSubType.instance(SubType.EQUIPMENT));
			SetGenerator thisIsEquipped = Intersect.instance(This.instance(), equippedThings);
			this.canApply = Both.instance(this.canApply, thisIsEquipped);

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
		}
	}

	public KorDuelist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// As long as Kor Duelist is equipped, it has double strike. (It deals
		// both first-strike and regular combat damage.)
		this.addAbility(new DoubleStrikeWhileEquipped(state));
	}
}

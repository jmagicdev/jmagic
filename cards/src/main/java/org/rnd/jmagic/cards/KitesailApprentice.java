package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kitesail Apprentice")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class KitesailApprentice extends Card
{
	public static final class BoostWhileEquipped extends StaticAbility
	{
		public BoostWhileEquipped(GameState state)
		{
			super(state, "As long as Kitesail Apprentice is equipped, it gets +1/+1 and has flying.");

			SetGenerator equippedThings = EquippedBy.instance(HasSubType.instance(SubType.EQUIPMENT));
			SetGenerator thisIsEquipped = Intersect.instance(This.instance(), equippedThings);
			this.canApply = Both.instance(this.canApply, thisIsEquipped);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public KitesailApprentice(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// As long as Kitesail Apprentice is equipped, it gets +1/+1 and has
		// flying.
		this.addAbility(new BoostWhileEquipped(state));
	}
}

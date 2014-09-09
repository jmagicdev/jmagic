package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Full Moon's Rise")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class FullMoonsRise extends Card
{
	public static final class FullMoonsRiseAbility0 extends StaticAbility
	{
		public FullMoonsRiseAbility0(GameState state)
		{
			super(state, "Werewolf creatures you control get +1/+0 and have trample.");

			SetGenerator werewolves = Intersect.instance(HasSubType.instance(SubType.WEREWOLF), CreaturePermanents.instance(), ControlledBy.instance(You.instance()));
			this.addEffectPart(modifyPowerAndToughness(werewolves, +1, +0));
			this.addEffectPart(addAbilityToObject(werewolves, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public static final class FullMoonsRiseAbility1 extends ActivatedAbility
	{
		public FullMoonsRiseAbility1(GameState state)
		{
			super(state, "Sacrifice Full Moon's Rise: Regenerate all Werewolf creatures you control.");
			this.addCost(sacrificeThis("Full Moon's Rise"));

			SetGenerator werewolves = Intersect.instance(HasSubType.instance(SubType.WEREWOLF), CreaturePermanents.instance(), ControlledBy.instance(You.instance()));
			this.addEffect(regenerate(werewolves, "Regenerate all Werewolf creatures you control."));
		}
	}

	public FullMoonsRise(GameState state)
	{
		super(state);

		// Werewolf creatures you control get +1/+0 and have trample.
		this.addAbility(new FullMoonsRiseAbility0(state));

		// Sacrifice Full Moon's Rise: Regenerate all Werewolf creatures you
		// control.
		this.addAbility(new FullMoonsRiseAbility1(state));
	}
}

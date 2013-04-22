package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rootwater Matriarch")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class RootwaterMatriarch extends Card
{
	public static final class ControlEnchanted extends ActivatedAbility
	{
		public ControlEnchanted(GameState state)
		{
			super(state, "(T): Gain control of target creature as long as that creature is enchanted.");

			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			SetGenerator duration = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(targetedBy(target)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			this.addEffect(createFloatingEffect(Not.instance(duration), "Gain control of target creature as long as that creature is enchanted", part));
		}
	}

	public RootwaterMatriarch(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new ControlEnchanted(state));
	}
}

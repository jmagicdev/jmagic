package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Merrow Reejerey")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.SOLDIER})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class MerrowReejerey extends Card
{
	public static final class MerrowReejereyAbility0 extends StaticAbility
	{
		public MerrowReejereyAbility0(GameState state)
		{
			super(state, "Other Merfolk creatures you control get +1/+1.");

			SetGenerator fish = Intersect.instance(HasSubType.instance(SubType.MERFOLK), CREATURES_YOU_CONTROL);
			SetGenerator otherFish = RelativeComplement.instance(fish, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherFish, +1, +1));
		}
	}

	public static final class MerrowReejereyAbility1 extends EventTriggeredAbility
	{
		public MerrowReejereyAbility1(GameState state)
		{
			super(state, "Whenever you cast a Merfolk spell, you may tap or untap target permanent.");

			SimpleEventPattern youCastFish = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			youCastFish.put(EventType.Parameter.PLAYER, You.instance());
			youCastFish.put(EventType.Parameter.OBJECT, HasSubType.instance(SubType.MERFOLK));
			this.addPattern(youCastFish);

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(youMay(tapOrUntap(target, "target permanent"), "You may tap or untap target permanent."));
		}
	}

	public MerrowReejerey(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other Merfolk creatures you control get +1/+1.
		this.addAbility(new MerrowReejereyAbility0(state));

		// Whenever you cast a Merfolk spell, you may tap or untap target
		// permanent.
		this.addAbility(new MerrowReejereyAbility1(state));
	}
}

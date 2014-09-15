package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siren of the Fanged Coast")
@Types({Type.CREATURE})
@SubTypes({SubType.SIREN})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class SirenoftheFangedCoast extends Card
{
	public static final class SirenoftheFangedCoastAbility2 extends EventTriggeredAbility
	{
		public SirenoftheFangedCoastAbility2(GameState state)
		{
			super(state, "When Siren of the Fanged Coast enters the battlefield, if tribute wasn't paid, gain control of target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect(Empty.instance(), "Gain control of target creature.", part));
		}
	}

	public SirenoftheFangedCoast(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Tribute 3 (As this creature enters the battlefield, an opponent of
		// your choice may place three +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 3));

		// When Siren of the Fanged Coast enters the battlefield, if tribute
		// wasn't paid, gain control of target creature.
		this.addAbility(new SirenoftheFangedCoastAbility2(state));
	}
}

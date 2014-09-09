package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master Thief")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class MasterThief extends Card
{
	public static final class MasterThiefAbility0 extends EventTriggeredAbility
	{
		public MasterThiefAbility0(GameState state)
		{
			super(state, "When Master Thief enters the battlefield, gain control of target artifact for as long as you control Master Thief.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			SetGenerator expires = Intersect.instance(ABILITY_SOURCE_OF_THIS, ControlledBy.instance(You.instance()));

			this.addEffect(createFloatingEffect(expires, "Gain control of target artifact for as long as you control Master Thief.", part));

		}
	}

	public MasterThief(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Master Thief enters the battlefield, gain control of target
		// artifact for as long as you control Master Thief.
		this.addAbility(new MasterThiefAbility0(state));
	}
}

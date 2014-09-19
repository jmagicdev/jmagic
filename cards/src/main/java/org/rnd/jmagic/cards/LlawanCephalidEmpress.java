package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Llawan, Cephalid Empress")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.CEPHALID})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class LlawanCephalidEmpress extends Card
{
	public static final class LlawanCephalidEmpressAbility0 extends EventTriggeredAbility
	{
		public LlawanCephalidEmpressAbility0(GameState state)
		{
			super(state, "When Llawan, Cephalid Empress enters the battlefield, return all blue creatures your opponents control to their owners' hands.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponentsBlueCreatures = Intersect.instance(HasColor.instance(Color.BLUE), CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			this.addEffect(bounce(opponentsBlueCreatures, "Return all blue creatures your opponents control to their owners' hands."));
		}
	}

	public static final class LlawanCephalidEmpressAbility1 extends StaticAbility
	{
		public LlawanCephalidEmpressAbility1(GameState state)
		{
			super(state, "Your opponents can't cast blue creature spells.");

			PlayProhibition prohibitPattern = new PlayProhibition(OpponentsOf.instance(You.instance()),//
			c -> c.colors.contains(Color.BLUE) && c.types.contains(Type.CREATURE));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public LlawanCephalidEmpress(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// When Llawan, Cephalid Empress enters the battlefield, return all blue
		// creatures your opponents control to their owners' hands.
		this.addAbility(new LlawanCephalidEmpressAbility0(state));

		// Your opponents can't cast blue creature spells.
		this.addAbility(new LlawanCephalidEmpressAbility1(state));
	}
}

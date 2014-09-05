package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Llawan, Cephalid Empress")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.CEPHALID})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Torment.class, r = Rarity.RARE)})
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

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.BLACK), HasType.instance(Type.CREATURE)));
			prohibitPattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
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

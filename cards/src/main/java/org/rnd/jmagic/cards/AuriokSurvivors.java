package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Auriok Survivors")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("5W")
@ColorIdentity({Color.WHITE})
public final class AuriokSurvivors extends Card
{
	public static final class AuriokSurvivorsAbility0 extends EventTriggeredAbility
	{
		public AuriokSurvivorsAbility0(GameState state)
		{
			super(state, "When Auriok Survivors enters the battlefield, you may return target Equipment card from your graveyard to the battlefield. If you do, you may attach it to Auriok Survivors.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), InZone.instance(GraveyardOf.instance(You.instance()))), "target Equipment card in your graveyard"));

			EventFactory toPlay = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target Equipment card from your graveyard to the battlefield.");
			toPlay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			toPlay.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			toPlay.parameters.put(EventType.Parameter.OBJECT, target);

			EventFactory attach = new EventFactory(EventType.ATTACH, "Attach it to Auriok Survivors.");
			attach.parameters.put(EventType.Parameter.CAUSE, This.instance());
			attach.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(EventResult.instance(Identity.instance(toPlay))));
			attach.parameters.put(EventType.Parameter.TARGET, This.instance());

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may return target Equipment card from your graveyard to the battlefield. If you do, you may attach it to Auriok Survivors.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(toPlay, "You may return target Equipment card from your graveyard to the battlefield.")));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(youMay(attach, "You may attach it to Auriok Survivors.")));
			this.addEffect(effect);
		}
	}

	public AuriokSurvivors(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// When Auriok Survivors enters the battlefield, you may return target
		// Equipment card from your graveyard to the battlefield. If you do, you
		// may attach it to Auriok Survivors.
		this.addAbility(new AuriokSurvivorsAbility0(state));
	}
}

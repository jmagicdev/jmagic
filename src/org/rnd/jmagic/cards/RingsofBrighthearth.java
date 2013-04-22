package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rings of Brighthearth")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({})
public final class RingsofBrighthearth extends Card
{
	public static final class LessThanThreePlaneswalkers extends EventTriggeredAbility
	{
		public LessThanThreePlaneswalkers(GameState state)
		{
			super(state, "Whenever you activate an ability, if it isn't a mana ability, you may pay (2). If you do, copy that ability. You may choose new targets for the copy.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			// Everything on the stack that isn't a spell should be an ability.
			// I'm pretty sure, anyway.
			pattern.withResult(RelativeComplement.instance(InZone.instance(Stack.instance()), Spells.instance()));
			this.addPattern(pattern);

			SetGenerator thatAbility = EventResult.instance(TriggerEvent.instance(This.instance()));

			this.interveningIf = Not.instance(ManaAbilityFilter.instance(thatAbility));

			EventFactory copyFactory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that ability. You may choose new targets for the copy.");
			copyFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copyFactory.parameters.put(EventType.Parameter.OBJECT, thatAbility);
			copyFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory mayFactory = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2).");
			mayFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayFactory.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(2)")));
			mayFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2). If you do, copy that ability. You may choose new targets for the copy.");
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayFactory));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(copyFactory));
			this.addEffect(ifFactory);
		}
	}

	public RingsofBrighthearth(GameState state)
	{
		super(state);

		this.addAbility(new LessThanThreePlaneswalkers(state));
	}
}

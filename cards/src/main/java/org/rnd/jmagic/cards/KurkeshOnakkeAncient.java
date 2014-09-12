package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Kurkesh, Onakke Ancient")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.OGRE})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class KurkeshOnakkeAncient extends Card
{
	public static final class KurkeshOnakkeAncientAbility0 extends EventTriggeredAbility
	{
		public KurkeshOnakkeAncientAbility0(GameState state)
		{
			super(state, "Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay (R). If you do, copy that ability. You may choose new targets for the copy.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(ActivatedAbilitiesOf.instance(ArtifactPermanents.instance()));
			this.addPattern(pattern);

			SetGenerator thatAbility = EventResult.instance(TriggerEvent.instance(This.instance()));

			this.interveningIf = Not.instance(ManaAbilityFilter.instance(thatAbility));

			EventFactory copyFactory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that ability. You may choose new targets for the copy.");
			copyFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copyFactory.parameters.put(EventType.Parameter.OBJECT, thatAbility);
			copyFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory mayFactory = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (R).");
			mayFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayFactory.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(R)")));
			mayFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (R). If you do, copy that ability. You may choose new targets for the copy.");
			ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayFactory));
			ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(copyFactory));
			this.addEffect(ifFactory);
		}
	}

	public KurkeshOnakkeAncient(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Whenever you activate an ability of an artifact, if it isn't a mana
		// ability, you may pay (R). If you do, copy that ability. You may
		// choose new targets for the copy.
		this.addAbility(new KurkeshOnakkeAncientAbility0(state));
	}
}

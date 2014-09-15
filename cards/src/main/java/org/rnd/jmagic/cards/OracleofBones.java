package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oracle of Bones")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.SHAMAN})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class OracleofBones extends Card
{
	public static final class OracleofBonesAbility2 extends EventTriggeredAbility
	{
		public OracleofBonesAbility2(GameState state)
		{
			super(state, "When Oracle of Bones enters the battlefield, if tribute wasn't paid, you may cast an instant or sorcery card from your hand without paying its mana cost.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			SetGenerator choosable = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(HandOf.instance(You.instance())));

			EventFactory mayCast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast an instant or sorcery card from your hand without paying its mana cost.");
			mayCast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayCast.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayCast.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			mayCast.parameters.put(EventType.Parameter.OBJECT, choosable);
			this.addEffect(mayCast);
		}
	}

	public OracleofBones(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Tribute 2 (As this creature enters the battlefield, an opponent of
		// your choice may place two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 2));

		// When Oracle of Bones enters the battlefield, if tribute wasn't paid,
		// you may cast an instant or sorcery card from your hand without paying
		// its mana cost.
		this.addAbility(new OracleofBonesAbility2(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rith, the Awakener")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3RGW")
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class RiththeAwakener extends Card
{
	public static final class RiththeAwakenerAbility1 extends EventTriggeredAbility
	{
		public RiththeAwakenerAbility1(GameState state)
		{
			super(state, "Whenever Rith, the Awakener deals combat damage to a player, you may pay (2)(G). If you do, choose a color, then put a 1/1 green Saproling creature token onto the battlefield for each permanent of that color.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			EventFactory payMana = new EventFactory(EventType.PAY_MANA, "Pay (2)(G)");
			payMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payMana.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)(G)")));
			payMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			EventFactory youMayPay = youMay(payMana, "You may pay (2)(G)");

			EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a color,");
			choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
			choose.parameters.put(EventType.Parameter.CHOICE, Identity.fromCollection(Color.allColors()));
			choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChooseReason.CHOOSE_COLOR, PlayerInterface.ChoiceType.COLOR));
			choose.parameters.put(EventType.Parameter.OBJECT, This.instance());

			SetGenerator ofThatColor = HasColor.instance(EffectResult.instance(choose));
			SetGenerator permanentsOfThatColor = Intersect.instance(Permanents.instance(), ofThatColor);
			SetGenerator forEachPermanentOfThatColor = Count.instance(permanentsOfThatColor);
			String tokenEffectName = "then put a 1/1 green Saproling creature token onto the battlefield for each permanent of that color";
			CreateTokensFactory tokens = new CreateTokensFactory(forEachPermanentOfThatColor, numberGenerator(1), numberGenerator(1), tokenEffectName);
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.SAPROLING);

			EventFactory ifThen = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2)(G). If you do, choose a color, then put a 1/1 green Saproling creature token onto the battlefield for each permanent of that color.");
			ifThen.parameters.put(EventType.Parameter.IF, Identity.instance(youMayPay));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(choose, tokens.getEventFactory())));
			this.addEffect(ifThen);
		}
	}

	public RiththeAwakener(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Rith, the Awakener deals combat damage to a player, you may
		// pay (2)(G). If you do, choose a color, then put a 1/1 green Saproling
		// creature token onto the battlefield for each permanent of that color.
		this.addAbility(new RiththeAwakenerAbility1(state));
	}
}

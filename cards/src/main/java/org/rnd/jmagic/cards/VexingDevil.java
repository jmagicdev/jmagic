package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vexing Devil")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class VexingDevil extends Card
{
	public static final class VexingDevilAbility0 extends EventTriggeredAbility
	{
		public VexingDevilAbility0(GameState state)
		{
			super(state, "When Vexing Devil enters the battlefield, any opponent may have it deal 4 damage to him or her. If a player does, sacrifice Vexing Devil.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponents = OpponentsOf.instance(You.instance());
			DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

			EventFactory takeDamage = permanentDealDamage(4, eachPlayer, "Have Vexing Devil deal 4 damage to you");

			EventFactory anyOpponentMay = new EventFactory(ANY_PLAYER_MAY, "Any opponent may have it deal 4 damage to him or her.");
			anyOpponentMay.parameters.put(EventType.Parameter.PLAYER, opponents);
			anyOpponentMay.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			anyOpponentMay.parameters.put(EventType.Parameter.EFFECT, Identity.instance(takeDamage));
			this.addEffect(ifThen(anyOpponentMay, sacrificeThis("Vexing Devil"), "If a player does, sacrifice Vexing Devil."));
		}
	}

	public VexingDevil(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// When Vexing Devil enters the battlefield, any opponent may have it
		// deal 4 damage to him or her. If a player does, sacrifice Vexing
		// Devil.
		this.addAbility(new VexingDevilAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sharuum the Hegemon")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SPHINX})
@ManaCost("3WUB")
@Printings({@Printings.Printed(ex = Expansion.FTV_LEGENDS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class SharuumtheHegemon extends Card
{
	public static final class HegeTrimmer extends EventTriggeredAbility
	{
		public HegeTrimmer(GameState state)
		{
			super(state, "When Sharuum the Hegemon enters the battlefield, you may return target artifact card from your graveyard to the battlefield.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card from your graveyard");

			EventFactory moveFactory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target artifact card from your graveyard to the battlefield");
			moveFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveFactory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			moveFactory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			this.addEffect(youMay(moveFactory, "You may return target artifact card from your graveyard to the battlefield."));
		}
	}

	public SharuumtheHegemon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new HegeTrimmer(state));
	}
}

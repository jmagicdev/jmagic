package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Protomatter Powder")
@Types({Type.ARTIFACT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class ProtomatterPowder extends Card
{
	public static final class CanWeFixIt extends ActivatedAbility
	{
		public CanWeFixIt(GameState state)
		{
			super(state, "(4)(W), (T), Sacrifice Protomatter Powder: Return target artifact card from your graveyard to the battlefield.");

			this.setManaCost(new ManaPool("4W"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Protomatter Powder"));

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card from your graveyard");

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target artifact card from your graveyard to the battlefield");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(factory);
		}
	}

	public ProtomatterPowder(GameState state)
	{
		super(state);

		this.addAbility(new CanWeFixIt(state));
	}
}

package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Champion extends Keyword
{
	public Champion(GameState state, String name)
	{
		super(state, name);
	}

	@Override
	abstract protected java.util.List<NonStaticAbility> createNonStaticAbilities();

	public abstract static class ChampionExileAbility extends EventTriggeredAbility
	{
		public ChampionExileAbility(GameState state, String filterName, SetGenerator filter, Class<? extends ChampionReturnAbility> link)
		{
			super(state, "When this permanent enters the battlefield, sacrifice it unless you exile another " + filterName + " you control.");

			this.addPattern(whenThisEntersTheBattlefield());

			this.getLinkManager().addLinkClass(link);

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator controller = ControllerOf.instance(thisCard);
			SetGenerator choices = Intersect.instance(RelativeComplement.instance(Intersect.instance(InZone.instance(Battlefield.instance()), filter), thisCard), ControlledBy.instance(controller));

			EventFactory sacFactory = sacrificeThis("this permanent");

			EventFactory exileFactory = new EventFactory(EventType.EXILE_CHOICE, ("Exile another " + filterName + " you control"));
			exileFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileFactory.parameters.put(EventType.Parameter.OBJECT, choices);
			exileFactory.parameters.put(EventType.Parameter.PLAYER, controller);
			exileFactory.setLink(this);

			this.addEffect(unless(You.instance(), sacFactory, exileFactory, "Sacrifice it unless you exile another " + filterName + " you control."));
		}
	}

	public abstract static class ChampionReturnAbility extends EventTriggeredAbility
	{
		public ChampionReturnAbility(GameState state, Class<? extends ChampionExileAbility> link)
		{
			super(state, "When this permanent leaves the battlefield, return the exiled card to the battlefield under its owner's control.");

			this.getLinkManager().addLinkClass(link);

			SetGenerator exiledObject = ChosenFor.instance(LinkedTo.instance(OriginalTrigger.instance(This.instance())));

			this.addPattern(whenThisLeavesTheBattlefield());

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return the exiled card to the battlefield under its owner's control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, exiledObject);
			this.addEffect(factory);
		}
	}
}
